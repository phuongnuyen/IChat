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

    public String strAvatarPath;

    public User(String userName, String firstName, String lastName, String email){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.age = 20;
        this.address = "221B Baker";
        this.gender = "Male";
        this.phoneNumber = "0999903182";
        //tạm thời test chức năng lấy hình về để lên ImageView thôi
        strAvatarPath = "gs://androidchatapp-6140a.appspot.com/trisinh/e20ade71309bd82899370d7421fc48.jpg";
    }

    public User(String userName, String firstName, String lastName, String email, String age, String address, String gender, String phoneNumber){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        if (!age.equals(""))
            this.age = Integer.parseInt(age);
        else
            this.age = 0;

        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;

        this.strAvatarPath = "";

    }

    public String GetName(){
        return firstName + " " + lastName;
    }

    public void SetAvatar(String strAvatarPath)
    {
        this.strAvatarPath = strAvatarPath;
    }

    public String GetAvatarPath() { return this.strAvatarPath; }
}
