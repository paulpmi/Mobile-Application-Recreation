package com.example.paulp.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button   registerButton;
    EditText usernameText;
    ListView list;
    ArrayAdapter<String> myarrayAdapter;
    int positionOf;
    List<String> used = new ArrayList<>();
    View savedView;
    AdapterView<?> adapterView;
    int ok =0;

    private void addItems(){
        List<String> l = new ArrayList<>();
        l.add("One");
        l.add("Two");
        l.add("Three");
        l.add("Four");
        l.add("Four2");
        l.add("Five");
        l.add("Six");

        for (int i = 0; i<l.size();i++)
            if (!this.used.contains(l.get(i))) {
                this.myarrayAdapter.add(l.get(i));
                this.used.add(l.get(i));
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setX(20);
        registerButton.setY(100);
        usernameText   = (EditText)findViewById(R.id.usernameText);
        usernameText.setX(registerButton.getX() - 30);
        usernameText.setY(registerButton.getY() - 80);
        //passwordText = (EditText)findViewById(R.id.passwordText);
        list = (ListView)findViewById(R.id.list1);
        list.setX(350);
        list.setY(50);

        ArrayList<String> myStringArray1 = new ArrayList<String>();

        List<String> myList = new ArrayList<String>();

        myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
        list.setAdapter(myarrayAdapter);
        list.setTextFilterEnabled(true);

        if (this.ok == 0)
            this.addItems();
        this.ok++;

        registerButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Log.v("EditText", usernameText.getText().toString());
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, usernameText.getText().toString());
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Laboratory1");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, usernameText.getText());

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();
                            Log.i("Finished sending email...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this,
                                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(MainActivity.this, ListViewChanger.class);
                        myIntent.putExtra("val", ((TextView) view).getText().toString());
                        positionOf = position;
                        adapterView = parent;
                        savedView = view;

                        startActivityForResult(myIntent, 0);
                        //String change = getIntent().getStringExtra("VALUE");
                        //((TextView) view).setText(change);
                        //myarrayAdapter.add(change);
                        //myarrayAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        String change = data.getStringExtra("VALUE");
        String old = data.getStringExtra("OLD");
        if (change != null) {
            List<String> myList = new ArrayList<String>();
            ArrayAdapter<String> myarrayAdaptercopy = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
            for(int i = 0; i < this.myarrayAdapter.getCount(); i++) {
                if (!this.myarrayAdapter.getItem(i).equals(old)) {
                    myarrayAdaptercopy.add(this.myarrayAdapter.getItem(i));
                    //copyList.add(this.myarrayAdapter.getItem(i));
                }
                else {
                    //copyList.add(change);
                    myarrayAdaptercopy.add(change);
                }
            }
            //myList = copyList;
            this.myarrayAdapter.clear();

            for(int i = 0; i < myarrayAdaptercopy.getCount(); i++) {
                this.myarrayAdapter.add(myarrayAdaptercopy.getItem(i));
            }
            //finish();
        }
    }

}
