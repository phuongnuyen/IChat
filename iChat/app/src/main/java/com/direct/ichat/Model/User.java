package com.direct.ichat.Model;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class User {
    public String userName;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;

//    more..

    public User(String userName, String firstName, String lastName, String email){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String GetName(){
        return firstName + " " + lastName;
    }
}
