package com.example.paulp.hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by paulp on 12/2/2017.
 */

public class RegisterScreen extends Activity {
    EditText username;
    EditText password;
    EditText email;
    Button loginButton;

    //Repositories cardRepo;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.register_screen);
        this.username = (EditText) findViewById(R.id.usernametext);
        this.password = (EditText) findViewById(R.id.passwordtext);
        this.email = (EditText) findViewById(R.id.emailText);
        this.loginButton = (Button) findViewById(R.id.registerButton);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final DatabaseReference reference =
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com/users");

        mAuth = FirebaseAuth.getInstance();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reference.child(username.getText().toString()).child("Email").setValue(email.getText().toString());
                //reference.child(username.getText().toString()).child("Password").setValue(password.getText().toString());
                //reference.child(username.getText().toString()).child("Cards").setValue(new HashMap<String, String>());
                //reference.push().setValue(password.getText().toString());
                ///final Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                //intent.putExtra("user", username.getText().toString());

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    intent.putExtra("user", user.getEmail());
                                    Log.d("SUCESS LOGIN", "Success LOGIN");
                                    startActivity(intent);
                                }
                                else
                                    Log.d("ERROR LOGIN", task.getException().toString());

                            }
                        });

                //startActivity(intent);
            }
        });
    }
}
