package com.direct.ichat.Activity;

import com.direct.ichat.Model.User;

import org.json.JSONObject;

public class UserDetails {
    public static String username = ""; //username của người dùng mới đăng nhập vào
    public static String password = "";
    //public static String chatWith = "";
    public static JSONObject obj; //tất cả dữ liệu được lấy xuống để thực hiện các thao tác khác
    public static User userChatWith; //Thông tin của người mình chat cùng
    public static User user; //Thông tin của người dùng vừa mới đăng nhập vào

}
