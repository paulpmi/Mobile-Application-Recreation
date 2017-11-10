package com.example.paulp.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by paulp on 10/29/2017.
 */

public class ListViewChanger extends AppCompatActivity {
    Button saveButton;
    EditText listText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        getActionBar();

        saveButton = (Button) findViewById(R.id.saveButton);
        listText = (EditText) findViewById(R.id.textName);

        final String change = getIntent().getStringExtra("val");
        listText.setText(change);

        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(ListViewChanger.this, MainActivity.class);
                        intent.putExtra("VALUE", listText.getText().toString());
                        intent.putExtra("OLD", change);
                        setResult(Activity.RESULT_OK, intent);
                        //startActivity(intent);
                        finish();
                    }
                }
        );

    }
}
