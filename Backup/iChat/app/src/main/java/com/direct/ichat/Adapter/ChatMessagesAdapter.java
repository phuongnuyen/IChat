package com.direct.ichat.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.direct.ichat.Model.ChatMessage;
import com.direct.ichat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/17/2016.
 */

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    private static final String TAG = "ChatMessagesAdapter";
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
        return new ViewHolder(activity, activity.getLayoutInflater().inflate(R.layout.item_message, parent, false));
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

        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.iv_author_avatar)
        ImageView ivAvatar;



        public ViewHolder(Activity activity, View itemView) {
            super(itemView);
            this.activity = activity;
            ButterKnife.bind(this, itemView);
            /*tvAuthorName = (TextView) itemView.findViewById(android.R.id.text1);
            tvMessage = (TextView) itemView.findViewById(android.R.id.text2);
            ivAvatar = new ImageView(activity);*/
            ((ViewGroup) itemView).addView(ivAvatar);


        }

        public void bind(ChatMessage chat) {
            tvAuthorName.setText(chat.authorName);

            //Message is an image
            if (chat.message.startsWith("https://firebasestorage.googleapis.com/") || chat.message.startsWith("content://")) {
                tvMessage.setVisibility(View.INVISIBLE);
                ivAvatar.setVisibility(View.VISIBLE);
                Glide.with(activity)
                        .load(chat.message)
                        .into(ivAvatar);
            } else {
                tvMessage.setVisibility(View.VISIBLE);
                ivAvatar.setVisibility(View.GONE);
                tvMessage.setText(chat.message);
            }
        }
    }
}
