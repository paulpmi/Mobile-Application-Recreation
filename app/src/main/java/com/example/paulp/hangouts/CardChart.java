package com.example.paulp.hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by paulp on 12/6/2017.
 */

public class CardChart extends Activity {

    GraphView graph;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_chart);

        graph = (GraphView) findViewById(R.id.graph);
        button = (Button) findViewById(R.id.chartGotoCards);

        final String user = getIntent().getStringExtra("user");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardChart.this, CardEditScreen.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        //final LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        final DatabaseReference reference =
                FirebaseDatabase.getInstance().getReferenceFromUrl("https://mobileapp-50d6f.firebaseio.com");
        reference.child("cards").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                DataPoint[] dataPoints = new DataPoint[30];
                int k = 0;
                for (DataSnapshot cards : dataSnapshot.getChildren()) {
                    if (cards.exists()) {
                        Card card = cards.getValue(Card.class);
                        Log.d("FOUNDED", card.attack + ' ' + card.health);
                        dataPoints[k] = new DataPoint(Integer.parseInt(card.attack), Integer.parseInt(card.health));
                        k++;
                    }
                }

                DataPoint[] dp = new DataPoint[k];
                for (int i = 0; i<k; i++) {
                    Log.d("DATAPOINT", dataPoints[i].toString());
                    dp[i] = dataPoints[i];
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                graph.addSeries(series);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
