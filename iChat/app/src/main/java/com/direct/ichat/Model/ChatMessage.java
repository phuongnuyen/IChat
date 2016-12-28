package com.direct.ichat.Model;

/**
 * Created by Phuong Nguyen Lan on 12/17/2016.
 */

public class ChatMessage {
    public int tag;
    public String authorName;
    public String message;

    public ChatMessage(String authorName, String message, int tag){
        this.authorName = authorName;
        this.message = message;
        this.tag = tag;
    }
}
