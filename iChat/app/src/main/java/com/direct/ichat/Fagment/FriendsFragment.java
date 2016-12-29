package com.direct.ichat.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.direct.ichat.Adapter.UserFriendAdapter;
import com.direct.ichat.Model.ChatMessage;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class FriendsFragment extends Fragment {
    List<User> friends;
    UserFriendAdapter adapter;

    @BindView(R.id.rcv_user_friends)
    RecyclerView rcvFriends;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        friends = new ArrayList<>();
        InitDummyData();

        rcvFriends.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvFriends.setLayoutManager(layoutManager);
        rcvFriends.setAdapter(adapter);

        return view;
    }

    public void InitDummyData(){
        friends.add(new User("najfe", "Nadeshiko", "2434", "abc@gmail.com"));
        friends.add(new User("21432", "Jim", "hohoho", "abc@gmail.com"));
        friends.add(new User("adkja", "Tromp", "arwerw", "abc@gmail.com"));
        friends.add(new User("aka17", "nkdjahfh", "ascz", "abc@gmail.com"));
        friends.add(new User("jslfih", "sgefw", "aete", "abc@gmail.com"));
    }
}
