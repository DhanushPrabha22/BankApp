package com.pocketcreations.bankapp;

public class User {

    String name;
    String location;
    Integer amount;
    Integer photoId;

    User(String name, String loc, int amo, int photoId) {
        this.name = name;
        this.location = loc;
        this.amount = amo;
        this.photoId = photoId;
    }
}
