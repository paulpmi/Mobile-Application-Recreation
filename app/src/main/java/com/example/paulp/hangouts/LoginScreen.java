package com.example.paulp.hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by paulp on 12/2/2017.
 */

public class LoginScreen extends Activity {
    EditText username;
    EditText password;
    //EditText email;
    Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference fReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_screen);
        this.username = (EditText) findViewById(R.id.loginName);
        this.password = (EditText) findViewById(R.id.loginPass);
        this.loginButton = (Button) findViewById(R.id.loginButton);

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        //fReference.child("users").child(username.getText().toString()).child()

        fReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.child("users").child(username.getText().toString()).getChildren()){
                    if (dataSnapshot.exists()) {
                        Intent intent = new Intent(LoginScreen.this, CardChart.class);
                        intent.putExtra("user", username.getText().toString());
                        startActivity(intent);
                    } else {
                        Log.d("NO SUCH USER: ", username.getText().toString());
                        Log.d("TRIAL ID: ", dataSnapshot.child(username.getText().toString()).toString());
                        Log.d("PASSWORD ID: ", data.child(password.getText().toString()).toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }
}
