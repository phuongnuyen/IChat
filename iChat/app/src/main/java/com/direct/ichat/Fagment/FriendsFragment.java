package com.direct.ichat.Fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.direct.ichat.Activity.MainActivity;
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

public class FriendsFragment extends Fragment {
    List<User> friends;
    OtherUserAdapter adapter;

    @BindView(R.id.rcv_friend)
    RecyclerView rcvFriends;

    JSONObject keyListFriend;
    //Firebase
    Firebase refFriendRequest;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);

        friends = new ArrayList<>();



        refFriendRequest = new Firebase("https://androidchatapp-6140a.firebaseio.com/users/" + UserDetails.username + "/ListFriend");

        refFriendRequest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //To do if user has FriendList update
                //vẫn update sau khi chuyển 3 tab nhưng lại trả về view trước khi lấy dữ liệu từ firebase xong
                //AddFriendList(dataSnapshot);


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


        InitListUserFriend();


        //fragment này tới đây trả về màn hình kết quả UI luôn kg có chế độ tự động update nếu có thay đổi
        return view;


    }

    private void InitListUserFriend(){
        //test
        InitDummyData();

        adapter = new OtherUserAdapter(friends, OtherUserAdapter.FRIEND_LIST);
        rcvFriends.setHasFixedSize(true);
        rcvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvFriends.setAdapter(adapter);

    }


    private void InitDummyData(){
//        friends.add(new User("najfe", "Nadeshiko", "2434", "abc@gmail.com"));
//        friends.add(new User("21432", "Jim", "hohoho", "abc@gmail.com"));
//        friends.add(new User("adkja", "Tromp", "arwerw", "abc@gmail.com"));
//        friends.add(new User("aka17", "nkdjahfh", "ascz", "abc@gmail.com"));
//        friends.add(new User("jslfih", "sgefw", "aete", "abc@gmail.com"));

        try {
            LoadFriendList();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void LoadFriendList() throws JSONException {

        try {
            if (UserDetails.obj.getJSONObject(UserDetails.username).has("ListFriend")) {
                keyListFriend = UserDetails.obj.getJSONObject(UserDetails.username).getJSONObject("ListFriend");

                Iterator i = keyListFriend.keys();
                String key = "";
                String firstName, lastName, email, username;



                while (i.hasNext()) {
                    key = i.next().toString();

                    if (key.equals(UserDetails.username))
                        continue;

                    username = key;
                    firstName = UserDetails.obj.getJSONObject(key).getString("FirstName");
                    lastName = UserDetails.obj.getJSONObject(key).getString("LastName");
                    email = UserDetails.obj.getJSONObject(key).getString("Email");

                    friends.add(new User(username, firstName, lastName, email));

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //chưa cần sài
    private void AddFriendList (DataSnapshot dataSnapshot)
    {
        String key = dataSnapshot.getKey();


        try {
            String firstName, lastName, email, username;

            username = key;
            firstName = UserDetails.obj.getJSONObject(key).getString("FirstName");
            lastName = UserDetails.obj.getJSONObject(key).getString("LastName");
            email = UserDetails.obj.getJSONObject(key).getString("Email");

            //~~~~~~~~~~~~~~~~~~do view chỉ được trả về 1 lần nên nếu có cập nhật trên view cũng đều kg được
            friends.add(new User(username, firstName, lastName, email));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
