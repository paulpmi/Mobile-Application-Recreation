package com.example.paulp.hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by paulp on 11/13/2017.
 */

public class EditScreen extends Activity {

    EditText nameRow;
    EditText typeRow;
    EditText descriptionRow;
    EditText position;
    Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_screen);

        nameRow = (EditText) findViewById(R.id.nameRow);
        typeRow = (EditText) findViewById(R.id.typeRow);
        descriptionRow = (EditText) findViewById(R.id.descriptionRow);
        saveButton = (Button) findViewById(R.id.save);

        final EditText pos1 = (EditText) findViewById(R.id.positionText);
        String data = getIntent().getStringExtra("data");
        final Integer pos = getIntent().getExtras().getInt("position");
        pos1.setText(pos.toString());
        String[] words = data.split(",");
        final String name = words[0];
        final String type = words[1];
        final String description = words[2];

        final Card u = new Card(name, type, description, "admin");
        nameRow.setText(name);
        typeRow.setText(type);
        descriptionRow.setText(description);

        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EditScreen.this, MainActivity.class);
                        setResult(Activity.RESULT_OK, intent);
                        intent.putExtra("pos", pos);
                        intent.putExtra("name", nameRow.getText().toString());
                        intent.putExtra("type", typeRow.getText().toString());
                        intent.putExtra("description", descriptionRow.getText().toString());
                        //startActivity(intent);
                        finish();
                    }
                }
        );
    }
}
