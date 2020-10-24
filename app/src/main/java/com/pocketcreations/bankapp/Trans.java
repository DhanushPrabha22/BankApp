package com.pocketcreations.bankapp;

public class Trans {

    String sender;
    String recipient;
    int amount;

    Trans(String s,String r,int amo){
        this.sender=s;
        this.recipient=r;
        this.amount=amo;
    }

}
