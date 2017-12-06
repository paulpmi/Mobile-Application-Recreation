package com.example.paulp.hangouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by paulp on 11/14/2017.
 */

public class CardAdapter extends ArrayAdapter<Card> {

    Context context;

    public CardAdapter(Context context, ArrayList<Card> cards){
        super(context, 0, cards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Card u = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }

        TextView Name = (TextView) convertView.findViewById(R.id.text1);
        TextView Description = (TextView) convertView.findViewById(R.id.text2);
        TextView Type = (TextView) convertView.findViewById(R.id.text3);
        TextView User = (TextView) convertView.findViewById(R.id.text4);
        TextView Attack = (TextView) convertView.findViewById(R.id.text5);
        TextView Health = (TextView) convertView.findViewById(R.id.text6);
        TextView Mana = (TextView) convertView.findViewById(R.id.text7);


        Name.setText(u.name);
        Description.setText(u.description);
        Type.setText(u.type);
        User.setText(u.user);
        Attack.setText(u.attack);
        Health.setText(u.health);
        Mana.setText(u.mana);

        return convertView;
    }
}
