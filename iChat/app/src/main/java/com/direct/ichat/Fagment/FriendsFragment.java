package com.direct.ichat.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.direct.ichat.Adapter.OtherUserAdapter;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class FriendsFragment extends Fragment {
    List<User> friends;
    OtherUserAdapter adapter;

    @BindView(R.id.rcv_friend)
    RecyclerView rcvFriends;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);

        InitListUserFriend();

        return view;
    }

    private void InitListUserFriend(){
        friends = new ArrayList<>();
        //test
        InitDummyData();

        adapter = new OtherUserAdapter(friends, OtherUserAdapter.FRIEND_LIST);
        rcvFriends.setHasFixedSize(true);
        rcvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvFriends.setAdapter(adapter);
    }


    private void InitDummyData(){
        friends.add(new User("najfe", "Nadeshiko", "2434", "abc@gmail.com"));
        friends.add(new User("21432", "Jim", "hohoho", "abc@gmail.com"));
        friends.add(new User("adkja", "Tromp", "arwerw", "abc@gmail.com"));
        friends.add(new User("aka17", "nkdjahfh", "ascz", "abc@gmail.com"));
        friends.add(new User("jslfih", "sgefw", "aete", "abc@gmail.com"));
    }
}
