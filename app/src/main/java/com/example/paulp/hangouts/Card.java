package com.example.paulp.hangouts;

import java.io.Serializable;

/**
 * Created by paulp on 11/13/2017.
 */

public class Card implements Serializable{

    public String name;
    public String type;
    public String description;
    //public User User;
    public String user;
    public String mana;
    public String health;
    public String attack;
    public String likes;

    public Card() {}

    public Card(String n , String t, String d, String u){
        this.name = n;
        this.type = t;
        this.description = d;
        this.user = u;
    }

    public Card(String n , String t, String d, String u, String m, String h, String a, String l){
        this.name = n;
        this.type = t;
        this.description = d;
        this.user = u;
        this.mana = m;
        this.health = h;
        this.attack = a;
        this.likes = l;
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }

    public String getDescription(){
        return this.description;
    }

    //public User getUser() { return this.User; }

    @Override
    public String toString(){
        return this.name + "," + this.type + "," + this.description + "," + this.user;
    }
}
