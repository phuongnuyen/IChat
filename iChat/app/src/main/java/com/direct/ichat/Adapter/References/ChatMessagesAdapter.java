package com.direct.ichat.Adapter.References;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.direct.ichat.Activity.UserDetails;
import com.direct.ichat.Model.ChatMessage;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/17/2016.
 */

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {
    private static final int TYPE_MESSAGE_TEXT = 0;
    private static final int TYPE_MESSAGE_IMAGE = 1;
    private Context mContext;
    private User user1;
    private User user2;
    private FirebaseStorage storage;

    List<ChatMessage> mMessages = new ArrayList<>();

    //user 1 chính là mình, user 2 là friend
    public  ChatMessagesAdapter(List<ChatMessage> listMessage){ //, User user1, User user2){
        mMessages = listMessage;
        this.user1 = user1;
        this.user2 = user2;
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
        mContext = parent.getContext();
        storage = FirebaseStorage.getInstance();

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
        @BindView(R.id.rl_group_message_text_1)
        RelativeLayout rlGroupText1;
        @BindView(R.id.ln_group_message_1)
        LinearLayout lnGroupMessage1;
        @BindView(R.id.tv_time_1)
        TextView tvTime1;
        @BindView(R.id.tv_message_1)
        TextView tvMessage1;
        @BindView(R.id.iv_avatar_1)
        ImageView ivAvatar1;
        @BindView(R.id.iv_message_image_1)
        ImageView ivMessageImg1;

        @BindView(R.id.rl_group_message_text_2)
        RelativeLayout rlGroupText2;
        @BindView(R.id.ln_group_message_2)
        LinearLayout lnGroupMessage2;
        @BindView(R.id.tv_time_2)
        TextView tvTime2;
        @BindView(R.id.tv_message_2)
        TextView tvMessage2;
        @BindView(R.id.iv_avatar_2)
        ImageView ivAvatar2;
        @BindView(R.id.iv_message_image_2)
        ImageView ivMessageImg2;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ChatMessage chat) {
            int type = TYPE_MESSAGE_TEXT;
            if (chat.message.length() > 6) {
                type = chat.message.substring(0, 6).equals("@I@M@A") == true
                        ? TYPE_MESSAGE_IMAGE : TYPE_MESSAGE_TEXT;
            }

            if (chat.tag == 0) {
                if (type == TYPE_MESSAGE_TEXT) {
                    tvTime1.setText(chat.authorName);
                    tvMessage1.setText(chat.message);
                    ivMessageImg1.setVisibility(View.GONE);

                }else{
                    //Image message
                    rlGroupText1.setVisibility(View.GONE);
                    ivMessageImg1.setVisibility(View.VISIBLE);
                    SetImage(chat.message.substring(6), ivMessageImg1);
                }
                lnGroupMessage2.setVisibility(View.GONE);
                SetImage(UserDetails.userChatWith.strAvatarPath, ivAvatar1);

            } else {
                if (type == TYPE_MESSAGE_TEXT) {
                    tvTime2.setText(chat.authorName);
                    tvMessage2.setText(chat.message);
                    ivMessageImg2.setVisibility(View.GONE);
                }else{
                    //Image message
                    rlGroupText2.setVisibility(View.GONE);
                    ivMessageImg2.setVisibility(View.VISIBLE);
                    SetImage(chat.message.substring(6), ivMessageImg2);
                }
                lnGroupMessage1.setVisibility(View.GONE);
                SetImage(UserDetails.userChatWith.strAvatarPath, ivAvatar2);
            }
        }

        private void SetImage(String imagePath, ImageView ivMessageImg){
            if (imagePath.equals("")){
                ivMessageImg.setImageResource(R.drawable.img_not_found);
            }

            StorageReference storageRef = storage.getReferenceFromUrl(imagePath);
            Glide.with(mContext)
                    .using(new FirebaseImageLoader())
                    .load(storageRef)
                    .into(ivMessageImg);
        }
    }
}
