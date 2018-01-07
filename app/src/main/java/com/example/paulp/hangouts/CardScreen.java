package com.example.paulp.hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by paulp on 12/5/2017.
 */

public class CardScreen extends Activity {

    ListView resultListView;
    Button createCardButton;
    ArrayList<Card> cardList = new ArrayList<Card>();
    CardAdapter CardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_screen);
        resultListView = (ListView) findViewById(R.id.cardList);

        createCardButton = (Button)findViewById(R.id.createCardButton);

        createCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardScreen.this, CardEditScreen.class);
                String user = getIntent().getStringExtra("user");
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        final DatabaseReference reference =
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com");

        reference.child("cards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Card card = dataSnapshot.getValue(Card.class);

                //DataSnapshot cardSnapshot = dataSnapshot.child("cards");
                //Iterable<DataSnapshot> cardChildren = cardSnapshot.getChildren();
                cardList.clear();
                for (DataSnapshot cards : dataSnapshot.getChildren())
                {
                    if (cards.exists()) {
                        Card card = cards.getValue(Card.class);
                        Log.d("CardName: ", card.name + "User" + card.user);
                        cardList.add(card);
                    }
                    else
                        Log.d("CardName: ", "ERROR NOT FOUND!");
                }
                //setRecyclerView();
                Log.d("Final: ", "THIS ENDED");
                for (Card c : cardList)
                    System.out.println(c);
                CardAdapter = new CardAdapter(CardScreen.this, cardList);
                resultListView.setAdapter(CardAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CardScreen.this, LikeScreen.class);
                intent.putExtra("data", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });



    }


}
