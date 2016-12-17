package com.direct.ichat.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.direct.ichat.Model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuong Nguyen Lan on 12/17/2016.
 */

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    private static final String TAG = "ChatMessageAdapter";
    private final Activity activity;
    List<ChatMessage> messages = new ArrayList<>();

    public ChatMessagesAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addMessage(ChatMessage chat) {
        messages.add(chat);
        notifyItemInserted(messages.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity, activity.getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Activity activity;

        TextView name, message;
        ImageView image;

        public ViewHolder(Activity activity, View itemView) {
            super(itemView);
            this.activity = activity;
            name = (TextView) itemView.findViewById(android.R.id.text1);
            message = (TextView) itemView.findViewById(android.R.id.text2);
            image = new ImageView(activity);
            ((ViewGroup) itemView).addView(image);

        }

        //Bind data model with View
        public void bind(ChatMessage chat) {
            name.setText(chat.authorName);
            //Message is an image
            if (chat.message.startsWith("https://firebasestorage.googleapis.com/") || chat.message.startsWith("content://")) {
                message.setVisibility(View.INVISIBLE);
                image.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(chat.message)
                        .into(image);
            } else {
                message.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                message.setText(chat.message);
            }
        }
    }
}
