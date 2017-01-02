package com.direct.ichat.Adapter.References;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.direct.ichat.Activity.UserDetails;
import com.direct.ichat.Model.ChatMessage;
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

        //Test avatar
        Context context;

        //Firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();



        @BindView(R.id.ln_group_message_1)
        LinearLayout lnGroupMessage1;
        @BindView(R.id.tv_time_1)
        TextView tvTime1;
        @BindView(R.id.tv_message_1)
        TextView tvMessage1;
        @BindView(R.id.iv_avatar_1)
        ImageView ivAvatar1;

        @BindView(R.id.ln_group_message_2)
        LinearLayout lnGroupMessage2;
        @BindView(R.id.tv_time_2)
        TextView tvTime2;
        @BindView(R.id.tv_message_2)
        TextView tvMessage2;
        @BindView(R.id.iv_avatar_2)
        ImageView ivAvatar2;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            this.context = itemView.getContext();
        }

        public void bind(ChatMessage chat) {
            if (chat.tag == 0) {
                tvTime1.setText(chat.authorName);
                tvMessage1.setText(chat.message);

                //~~~~~~~~~~~~~~~~~~~~~test avatar

                if(UserDetails.user.strAvatarPath.equals(""))
                {
                    //to do something in here;
                }
                else
                {
                    StorageReference storageRef = storage.getReferenceFromUrl(UserDetails.user.strAvatarPath);



                    Glide.with(context)
                            .using(new FirebaseImageLoader())
                            .load(storageRef)
                            .into(ivAvatar1);

                }


                //~~~~~~~~~~~~~~~~~~~~~~

                lnGroupMessage2.setVisibility(View.GONE);

            } else {
                tvTime2.setText(chat.authorName);
                tvMessage2.setText(chat.message);

                //~~~~~~~~~~~~~~~~~~~~~test avatar

                if(UserDetails.userChatWith.strAvatarPath.equals(""))
                {
                    //to do something in here;
                }
                else
                {
                    StorageReference storageRef = storage.getReferenceFromUrl(UserDetails.userChatWith.strAvatarPath);



                    Glide.with(context)
                            .using(new FirebaseImageLoader())
                            .load(storageRef)
                            .into(ivAvatar2);

                }


                //~~~~~~~~~~~~~~~~~~~~~~


                lnGroupMessage1.setVisibility(View.GONE);
            }
        }
    }
}
