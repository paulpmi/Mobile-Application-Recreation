package com.example.paulp.hangouts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Card> CardList = new ArrayList<>();
    CardAdapter CardAdapter;
    ListView resultListView;
    Button registerButton;
    EditText CardnameText;

    Button cardButton;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = (Button)findViewById(R.id.button);
        registerButton.setX(500);
        registerButton.setY(200);
        CardnameText   = (EditText)findViewById(R.id.editText);
        CardnameText.setX(registerButton.getX() - 20);
        CardnameText.setY(registerButton.getY() - 80);

        cardButton = (Button) findViewById(R.id.cardListButton);
        CardAdapter = new CardAdapter(this, CardList);
        //ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, Cardnames);
        addItems();

        resultListView = (ListView) findViewById(R.id.results_listview);
        resultListView.setAdapter(CardAdapter);

        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardScreen.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        /*
                        Log.v("EditText", CardnameText.getText().toString());
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, CardnameText.getText().toString());
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Laboratory1");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, CardnameText.getText());

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                    */

                        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(intent);
                    }


                });

        resultListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                        Intent intent = new Intent(MainActivity.this, EditScreen.class);
                        intent.putExtra("position", position);
                        intent.putExtra("data", adapter.getItemAtPosition(position).toString());
                        startActivityForResult(intent, 0);
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        String name = data.getStringExtra("name");
        String type = data.getStringExtra("type");
        String desc = data.getStringExtra("description");
        String pos = data.getStringExtra("pos");
        //System.out.println(data);
        //String[] words = data.split(",");
        if (name != null) {
            Card u = new Card(name, type, desc, "admin");
            CardList.set(Integer.parseInt(pos), u);
            CardAdapter.notifyDataSetChanged();/*
            ArrayList<Card> list = new ArrayList<>();
            CardAdapter copyadapter = new CardAdapter(this, list);
            for(int i = 0; i < this.CardAdapter.getCount(); i++)
                if (!this.CardAdapter.getItem(i).name.equals(name))
                    copyadapter.add(this.CardAdapter.getItem(i));
                else
                    copyadapter.add(u);
            CardAdapter.clear();
            for (int i = 0; i< copyadapter.getCount(); i++)
                CardAdapter.add(copyadapter.getItem(i));*/
        }
    }

    private void addItems(){
        CardList.add(new Card("Doomsayer", "Minion", "At the start of your turn destroy ALL minions", "admin"));
        CardList.add(new Card("Novice Engineer", "Minion", "Draw a card", "admin"));
        CardList.add(new Card("Argent Squire", "Minion", "Divine Shield", "admin"));
    }


    private void initialize(){

    }
}
