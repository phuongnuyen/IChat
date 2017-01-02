package com.direct.ichat.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.direct.ichat.Activity.UserDetails;
import com.direct.ichat.Adapter.OtherUserAdapter;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class WaitingForAcceptFragment extends Fragment {
    List<User> users;
    OtherUserAdapter adapter;

    JSONObject keyFriendRequest;
    //Firebase
    Firebase refFriendRequest;


    @BindView(R.id.rcv_waiting_user)
    RecyclerView rcvWaitingUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_for_accept, container, false);
        ButterKnife.bind(this, view);

        users = new ArrayList<>();

        refFriendRequest = new Firebase("https://androidchatapp-6140a.firebaseio.com/users/" + UserDetails.username + "/FriendRequest");

        InitDummyData();

        refFriendRequest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //To do if user has FriendRequest

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        adapter = new OtherUserAdapter(users, OtherUserAdapter.WAITING_LIST);
        rcvWaitingUser.setHasFixedSize(true);
        rcvWaitingUser.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvWaitingUser.setAdapter(adapter);

        return view;
    }

    private void InitDummyData(){

//        users.add(new User("najfe", "Nadeshiko", "2434", "abc@gmail.com"));
//        users.add(new User("21432", "Jim", "hohoho", "abc@gmail.com"));
//        users.add(new User("adkja", "Tromp", "arwerw", "abc@gmail.com"));
//        users.add(new User("aka17", "nkdjahfh", "ascz", "abc@gmail.com"));
//        users.add(new User("jslfih", "sgefw", "aete", "abc@gmail.com"));

        LoadFriendRequest();

    }

    private void LoadFriendRequest(){

        try {

            if (UserDetails.obj.getJSONObject(UserDetails.username).has("FriendRequest")) {

                keyFriendRequest = UserDetails.obj.getJSONObject(UserDetails.username).getJSONObject("FriendRequest");

                Iterator i = keyFriendRequest.keys();
                String key = "";
                String firstName, lastName, email, username, age, address, gender, phoneNumber;

                while (i.hasNext()) {
                    key = i.next().toString();
                    if (key.equals(UserDetails.username))
                        continue;
                    username = key;
                    firstName = UserDetails.obj.getJSONObject(key).getString("FirstName");
                    lastName = UserDetails.obj.getJSONObject(key).getString("LastName");
                    email = UserDetails.obj.getJSONObject(key).getString("Email");
                    age = UserDetails.obj.getJSONObject(key).getString("Age");
                    address = UserDetails.obj.getJSONObject(key).getString("Address");
                    gender = UserDetails.obj.getJSONObject(key).getString("Gender");
                    phoneNumber = UserDetails.obj.getJSONObject(key).getString("PhoneNumber");

                    User a = new User(username, firstName, lastName, email, age, address, gender, phoneNumber);
                    a.SetAvatar(UserDetails.obj.getJSONObject(key).getString("AvatarPath"));


                    users.add(a);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //vẫn chưa thực thi được với lý do như phần FriendFragment
    private void AddFriendRequest (DataSnapshot dataSnapshot)
    {

    }

}
