package com.direct.ichat.Model;

import java.io.Serializable;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class User implements Serializable{
    public String userName;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public int age;
    public String address;
    public String gender;

//    more..

    public User(String userName, String firstName, String lastName, String email){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.age = 20;
        this.address = "221B Baker";
        this.gender = "Male";
        this.phoneNumber = "0999903182";
    }

    public String GetName(){
        return firstName + " " + lastName;
    }
}
