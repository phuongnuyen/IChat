package com.direct.ichat.Model;

/**
 * Created by Phuong Nguyen Lan on 12/17/2016.
 */

public class ChatMessage {
    public String authorName;
    public String message;

    public ChatMessage(){}
    public ChatMessage(String authorName, String message){
        this.authorName = authorName;
        this.message = message;
    }
}
