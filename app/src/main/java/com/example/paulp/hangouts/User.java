package com.example.paulp.hangouts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulp on 12/2/2017.
 */

public class User implements Serializable {
    public String Username;
    public String Password;
    List<Card> Cards;

    public User(String u, String pass){
        this.Username = u;
        this.Password = pass;
        this.Cards = new ArrayList<>();
    }

    @Override
    public String toString(){
        return this.Username + " " + this.Password;
    }
}
