package com.direct.ichat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.ichat.Activity.ChatBoxActivity;
import com.direct.ichat.Activity.MainActivity;
import com.direct.ichat.Activity.ProfileActivity;
import com.direct.ichat.Activity.UserDetails;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class OtherUserAdapter  extends RecyclerView.Adapter<OtherUserAdapter.ViewHolder> {
    public static final int WAITING_LIST = 0;
    public static final int SEARCH_LIST = 1;
    public static final int FRIEND_LIST = 2;
    public static final String KEY_USER = "user";
    public static final String KEY_TYPE = "type";


    private int type = WAITING_LIST;
    private List<User> users = new ArrayList<>();

    public  OtherUserAdapter(List<User> listFriends, int type){
        users = listFriends;
        this.type = type;
    }

    public void AddOtherUser(User chat) {
        users.add(chat);
        notifyItemInserted(users.size());
    }

    public void RemoveOtherUser(int position){
        users.remove(position);
        notifyItemRemoved(position);
    }

    public User GetOtherUser(int position){
        return users.get(position);
    }

    @Override
    public OtherUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.other_user_item, parent, false);
        return new OtherUserAdapter.ViewHolder(inflatedView, type);
    }

    @Override
    public void onBindViewHolder(OtherUserAdapter.ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private int type;
        User user;
        Context context;

        @BindView(R.id.ln_other_user_item)
        LinearLayout lnOtherUserItem;
        @BindView(R.id.iv_other_user_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_other_user_name)
        TextView tvName;
        @BindView(R.id.tv_other_user_email)
        TextView tvEmail;
        @BindView(R.id.rl_other_user_group_btn)
        RelativeLayout rlGroupBtn;
        @BindView(R.id.btn_accept)
        RelativeLayout btnAccept;
        @BindView(R.id.btn_dismiss)
        RelativeLayout btnDismiss;
        @BindView(R.id.btn_add)
        RelativeLayout btnAdd;
        @BindView(R.id.btn_more)
        RelativeLayout btnMore;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.type = type;
            this.context = itemView.getContext();

            if (type == WAITING_LIST) {
                btnAdd.setVisibility(View.GONE);
                btnAdd.setEnabled(false);
                rlGroupBtn.setVisibility(View.VISIBLE);
                rlGroupBtn.setEnabled(true);
                btnMore.setVisibility(View.GONE);
                btnMore.setEnabled(false);
            }
            if (type == SEARCH_LIST) {
                btnAdd.setVisibility(View.VISIBLE);
                btnAdd.setEnabled(true);
                rlGroupBtn.setVisibility(View.GONE);
                rlGroupBtn.setEnabled(false);
                btnMore.setVisibility(View.GONE);
                btnMore.setEnabled(false);
            }
            if (type == FRIEND_LIST){
                btnAdd.setVisibility(View.GONE);
                btnAdd.setEnabled(false);
                rlGroupBtn.setVisibility(View.GONE);
                rlGroupBtn.setEnabled(false);
                btnMore.setVisibility(View.VISIBLE);
                btnMore.setEnabled(true);
            }

            lnOtherUserItem.setOnClickListener(this);
            btnAccept.setOnClickListener(this);
            btnDismiss.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
            btnMore.setOnClickListener(this);
        }

        public void bind(User user) {
            this.user = user;
            tvName.setText(user.GetName());
            tvEmail.setText(user.email);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ln_other_user_item:
                    if (type == WAITING_LIST || type == SEARCH_LIST) {
                        Intent intent = new Intent(context, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(KEY_USER, user);
                        bundle.putSerializable(KEY_TYPE, type);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else {
                        //truyền tham số với cách này dễ hơn
                        UserDetails.userChatWith = user;

                        Intent intent = new Intent(context, ChatBoxActivity.class);
                        context.startActivity(intent);
                    }
                    break;

                case R.id.btn_accept:
                    break;

                case R.id.btn_dismiss:
                    break;

                case R.id.btn_add:
                    break;

                case R.id.btn_more:
                    final PopupMenu popup = new PopupMenu(context, btnMore);
                    popup.getMenuInflater()
                            .inflate(R.menu.friend_drop_down_popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mn_view_profile:
                                    Intent intent = new Intent(context, ProfileActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(KEY_USER, user);
                                    bundle.putSerializable(KEY_TYPE, type);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    return true;

                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                    break;
            }
        }
    }
}