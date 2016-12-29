package com.direct.ichat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.direct.ichat.Model.ChatMessage;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class UserFriendAdapter extends RecyclerView.Adapter<UserFriendAdapter.ViewHolder> {

    List<User> mFriends = new ArrayList<>();


    public  UserFriendAdapter(List<User> listFriends){
        mFriends = listFriends;
    }

    public void AddFriend(User chat) {
        mFriends.add(chat);
        notifyItemInserted(mFriends.size());
    }

    public void RemoveFriend(int position){
        mFriends.remove(position);
        notifyItemRemoved(position);
    }

    public User GetFriend(int position){
        return mFriends.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.user_friend_item, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mFriends.get(position));
    }

    @Override
    public int getItemCount() {
    return mFriends.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_friend_avatar)
        ImageView ivFriendAvatar;
        @BindView(R.id.tv_friend_name)
        TextView tvFriendName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(User user) {
            tvFriendName.setText(user.GetName());
        }
    }
}
