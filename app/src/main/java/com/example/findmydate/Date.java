package com.example.findmydate;

/**
 * Created by kevincrazykid on 25/08/17.
 */

public class Date {
    protected String UidFrom;
    protected String UidTo;
    protected String location;

    public Date(User From, User To, String location){
        this.UidFrom = From.getUid();
        this.UidTo = To.getUid();
    }

}
