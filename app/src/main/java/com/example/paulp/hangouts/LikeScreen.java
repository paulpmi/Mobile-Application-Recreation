package com.example.paulp.hangouts;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by paulp on 12/6/2017.
 */

public class LikeScreen extends Activity{

    EditText cardName;
    EditText cardCreator;

    Button likeButton;
    Button favButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like_screen);

        cardName = (EditText) findViewById(R.id.cardNameLike);
        cardCreator = (EditText) findViewById(R.id.cardCreatorLike);
        likeButton = (Button) findViewById(R.id.buttonLike);
        favButton = (Button) findViewById(R.id.buttonFavorite);


        final String data = getIntent().getStringExtra("data");
        final String[] splittedData = data.split(",");

        cardName.setText(splittedData[0]);
        cardCreator.setText(splittedData[3]);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com");;

        final Integer[] likes = new Integer[1];

        reference.child("cards").child(splittedData[0]).child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    likes[0] = Integer.parseInt(dataSnapshot.getValue(String.class));
                    likes[0]++;
                    Log.d("LIKES", likes[0].toString());
                    likeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reference.child("cards").child(splittedData[0]).child("likes").setValue(likes[0].toString());
                            String token = FirebaseInstanceId.getInstance().getToken();

                            //String msg = getString(R.string.msg_token_fmt, token);
                            //reference.child("cards").child(splittedData[0]).child("token").setValue(token);
                            //Toast.makeText(LikeScreen.this, token, Toast.LENGTH_SHORT).show();

                            Map<String, String> not = new HashMap<>();
                            not.put("user", splittedData[3]);
                            not.put("message", "Your card got Liked");

                            reference.child("notifications").child("likes").push().setValue(not);

                                reference.child("usersTokens").child(splittedData[3]).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                        String uidToken = dataSnapshot.getValue().toString();
                                            Log.d("DBSNAPSHOT$ ", dataSnapshot.getValue().toString());
                                            JSONArray js = new JSONArray("["+"]");

                                        sendMessage(js, uidToken ,"Your card got Liked", "Your card got Liked", "Your card got Liked");
                                        }
                                        catch(Exception e) { Toast.makeText(LikeScreen.this, e.toString(), Toast.LENGTH_SHORT).show(); }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                        }
                    });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR OCCURED", databaseError.getMessage());
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if (user == null) {
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(LikeScreen.this, "You are not logged in");
                }
                else {
                    reference.child("users").child(user.getUid()).push().setValue(splittedData[3]);

                    FirebaseMessaging.getInstance().subscribeToTopic(splittedData[3]);

                    Map<String, String> not = new HashMap<>();
                    not.put("user", splittedData[3]);
                    not.put("sub", user.getUid());

                    //reference.child("notifications").child("favs").push().setValue(not);

                    //RequestQueue queue = Volley.newRequestQueue(this);

                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    reference.child("subs").child(splittedData[3]).push().setValue(refreshedToken);


                }
            }
        });
    }


    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    OkHttpClient mClient = new OkHttpClient();

    public void sendMessage(final JSONArray recipients, final String otherPhone, final String title, final String body, final String message) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    //notification.put("icon", "https://www.stonehorses.com/media/menu/modelhorse.jpg");

                    JSONObject data = new JSONObject();
                    data.put("message", message);

                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("to", otherPhone);

                    String result = postToFCM(root.toString());
                    Log.d(TAG, "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                //super(result);

                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");

                    Toast.makeText(LikeScreen.this, "Message Success: " + success + "Message Failed: " + failure + "\nPower:" + resultJson.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LikeScreen.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String postToFCM(String bodyString) throws IOException {
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key="
                        + "AIzaSyBPTuYO2_3xLBYDYYvFm0ADqAIiDbXiT3Y")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


}
