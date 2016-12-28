package com.direct.ichat.Adapter.References;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private static final String TAG = "CHAT_MESSAGE";
//    private final Activity activity;
    List<ChatMessage> mMessages = new ArrayList<>();


    public  ChatMessagesAdapter(List<ChatMessage> listMessage){
        mMessages = listMessage;
    }




    public void AddMessage(ChatMessage chat) {
        mMessages.add(chat);
        notifyItemInserted(mMessages.size());
    }

    public void RemoveMessage(int position){
        mMessages.remove(position);
        notifyItemRemoved(position);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.iv_author_avatar)
        ImageView ivAvatar;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ChatMessage chat) {
            tvAuthorName.setText(chat.authorName);
            tvMessage.setText(chat.message);
        }
    }
}
