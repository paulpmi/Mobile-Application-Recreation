package com.example.paulp.hangouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        cardName = (EditText) findViewById(R.id.cardName);
        cardType = (EditText) findViewById(R.id.cardType);
        cardDesc = (EditText) findViewById(R.id.cardDescription);
        cardMana = (EditText) findViewById(R.id.cardMana);
        cardHealth = (EditText) findViewById(R.id.cardHealth);
        cardAttack = (EditText) findViewById(R.id.cardAttack);
        saveButton = (Button) findViewById(R.id.cardSave);
        deleteButton = (Button) findViewById(R.id.deleteCard);
        n = (NumberPicker)findViewById(R.id.typePicker);

        final String[] a={"Battlecry","Charge","Deathrattle","Active"};

        n.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        n.setDisplayedValues(a);
        n.setMaxValue(3);
        n.setMinValue(0);
        n.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.e(null, newVal+"");
                cardType.setText(a[newVal]);
            }
        });

        final DatabaseReference reference =
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String user = getIntent().getStringExtra("user");
                    reference.child("users").child(user).child("cards").child(cardName.getText().toString()).setValue(0);
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("name", cardName.getText().toString());
                    hashMap.put("mana", cardMana.getText().toString());
                    hashMap.put("type", cardType.getText().toString());
                    hashMap.put("desciption", cardDesc.getText().toString());
                    hashMap.put("health", cardHealth.getText().toString());
                    hashMap.put("attack", cardAttack.getText().toString());
                    hashMap.put("likes", "0");
                    hashMap.put("user", user);
                    reference.child("cards").child(cardName.getText().toString()).setValue(hashMap);
                    reference.child("users").child(user).child("cards").child(cardName.getText().toString()).setValue(0);
                }
                catch (Exception e) { ViewDialog alert = new ViewDialog(); alert.showDialog(CardEditScreen.this, "You are not logged in"); }
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
                }
                catch (Exception e)
                {
                    ViewDialog alert = new ViewDialog(); alert.showDialog(CardEditScreen.this, e.toString());
                }
            }
        });

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);

    }

}
