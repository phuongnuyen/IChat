package com.direct.ichat.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.direct.ichat.Model.ChatMessage;

/**
 * Created by Phuong Nguyen Lan on 12/28/2016.
 */

public class MessageItemAdapter extends ArrayAdapter<ChatMessage>{
    private Context mContext;


    public MessageItemAdapter(Context context, int resource) {
        super(context, resource);
    }
}
