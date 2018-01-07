package com.example.paulp.hangouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by paulp on 12/2/2017.
 */

public class CardEditScreen extends Activity implements NumberPicker.OnValueChangeListener {
    List<Card> cards = new ArrayList<>();
    List<Card> userCards = new ArrayList<>();

    EditText cardName;
    EditText cardType;
    EditText cardDesc;
    EditText cardMana;
    EditText cardHealth;
    EditText cardAttack;
    Button saveButton;
    Button deleteButton;
    NumberPicker n;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_editscreen);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        cardName = (EditText) findViewById(R.id.cardName);
        cardType = (EditText) findViewById(R.id.cardType);
        cardDesc = (EditText) findViewById(R.id.cardDescription);
        cardMana = (EditText) findViewById(R.id.cardMana);
        cardHealth = (EditText) findViewById(R.id.cardHealth);
        cardAttack = (EditText) findViewById(R.id.cardAttack);
        saveButton = (Button) findViewById(R.id.cardSave);
        deleteButton = (Button) findViewById(R.id.deleteCard);
        n = (NumberPicker) findViewById(R.id.typePicker);

        final String[] a = {"Battlecry", "Charge", "Deathrattle", "Active"};

        n.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        n.setDisplayedValues(a);
        n.setMaxValue(3);
        n.setMinValue(0);
        n.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.e(null, newVal + "");
                cardType.setText(a[newVal]);
            }
        });

        final DatabaseReference reference =
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com/");


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        //String user = getIntent().getStringExtra("user");
                        //reference.child("users").child(user).child("cards").child(cardName.getText().toString()).setValue(0);
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("name", cardName.getText().toString());
                        hashMap.put("mana", cardMana.getText().toString());
                        hashMap.put("type", cardType.getText().toString());
                        hashMap.put("desciption", cardDesc.getText().toString());
                        hashMap.put("health", cardHealth.getText().toString());
                        hashMap.put("attack", cardAttack.getText().toString());
                        hashMap.put("likes", "0");
                        hashMap.put("user", user.getUid().toString());
                        reference.child("cards").child(cardName.getText().toString()).setValue(hashMap);
                        //reference.push().child("workkiasfa");
                        //reference.child("users").child(user).child("cards").child(cardName.getText().toString()).setValue(0);

                        reference.child("subs").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String uidToken;
                                Log.d("STARTED", dataSnapshot.getKey());
                                String message = user.getDisplayName() + " added a new card";
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    uidToken = ds.getValue(String.class);
                                    Log.d("LOG1$", ds.getValue(String.class));
                                    sendMessage(uidToken, "New Card", message, message);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        finish();
                    } catch (Exception e) {
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(CardEditScreen.this, "You are not logged in");
                    }
                /*reference.child("cards").child(cardName.getText().toString()).child("mana")
                        .setValue(cardMana.getText().toString());
                reference.child("cards").child(cardName.getText().toString()).child("type")
                        .setValue(cardType.getText().toString());
                reference.child("cards").child(cardName.getText().toString()).child("description")
                        .setValue(cardDesc.getText().toString());
                reference.child("cards").child(cardName.getText().toString()).child("health")
                        .setValue(cardHealth.getText().toString());
                reference.child("cards").child(cardName.getText().toString()).child("attack")
                        .setValue(cardAttack.getText().toString());
                reference.child("cards").child(cardName.getText().toString()).child("likes").setValue(0);
                reference.child("cards").child(cardName.getText().toString()).child("User").setValue(user);*/
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String user = getIntent().getStringExtra("user");
                        reference.child("cards").child(cardName.getText().toString()).removeValue();
                        reference.child("users").child(user).child("cards").child(cardName.getText().toString()).removeValue();
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(CardEditScreen.this, "You deleted Something M8");
                    } catch (Exception e) {
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog(CardEditScreen.this, e.toString());
                    }
                }
            });

        }
        else {
            ViewDialog alert = new ViewDialog();
            alert.showDialog(CardEditScreen.this, "You cannot create cards not logged in");
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    OkHttpClient mClient = new OkHttpClient();

    public void sendMessage(final String otherPhone, final String title, final String body, final String message) {

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

                    Toast.makeText(CardEditScreen.this, "Message Success: " + success + "Message Failed: " + failure + "\nPower:" + resultJson.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CardEditScreen.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
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
