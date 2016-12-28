package com.example.trisinh.androidchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListFriend extends AppCompatActivity {

    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    ArrayAdapter<String> adapter;

    EditText searchUsersBar;

    int totalUsers = 0;
    ProgressDialog pd;

    private FirebaseDatabase database;
    //Params:
    // - refUser: là biến để chỉ đường link URL vào ListFriend của username để thực hiện thao tác liên quan
    // - refFriend: là biến dùng để chỉ đường link URL vào Friend mà username thực hiện các thao tác liên quan (add, delete friend)
    private DatabaseReference refUser, refFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);
        setContentView(R.layout.activity_users);

        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);

        searchUsersBar = (EditText) findViewById(R.id.searchUsersBar);

        database  = FirebaseDatabase.getInstance();

        pd = new ProgressDialog(ListFriend.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://androidchatapp-6140a.firebaseio.com/users/" + UserDetails.username + "/ListFriend.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ListFriend.this);
        rQueue.add(request);


        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //UserDetails.chatWith = al.get(position);
                //Lấy item mà ArrayAdapter đang giữ (lấy nguyên kiểu dữ liệu ở đó luôn, Trong trường hợp này là String)
                UserDetails.chatWith = adapter.getItem(position);
                startActivity(new Intent(ListFriend.this, Chat.class));


                //~~~~~~~~~~~~~~~~~~~
                //Test chuc nang xoa listfriend
                DeleteFriend(adapter.getItem(position));
            }
        });

        searchUsersBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String searchText = searchUsersBar.getText().toString().toLowerCase();
                adapter.getFilter().filter(searchText);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void doOnSuccess(String s) {

        al.clear();
        totalUsers = 0;

        //usersList.removeAllViews();
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()) {
                key = i.next().toString();

                if (!key.equals(UserDetails.username)) {
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (totalUsers < 1) {
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        } else {
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, al);
            usersList.setAdapter(adapter);
        }

        pd.dismiss();
    }

    //Hoàn thiện delete Friend (chỉ còn râu ria là confirm có muốn delete friend hay không thôi
    protected void DeleteFriend (String StrFriend)
    {

        if (StrFriend != "")
        {
            refUser = database.getReferenceFromUrl("https://androidchatapp-6140a.firebaseio.com/users")
                    .child(UserDetails.username)
                    .child("ListFriend")
                    .child(StrFriend);
            refFriend = database.getReferenceFromUrl("https://androidchatapp-6140a.firebaseio.com/users")
                    .child(StrFriend)
                    .child("ListFriend")
                    .child(UserDetails.username);

            refUser.removeValue();
            refFriend.removeValue();


            refUser.removeEventListener(new ChildEventListener(){
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot)
                {
                    //adapter.clear();
                    //RefeshList();
                    System.out.println("Removed successfully");
                }

                @Override
                public void onCancelled (DatabaseError databaseError)
                {

                }

                @Override
                public void onChildAdded (DataSnapshot dataSnapshot, String s)
                {

                }

                @Override
                public void onChildChanged (DataSnapshot dataSnapshot, String s)
                {

                }

                @Override
                public void onChildMoved (DataSnapshot dataSnapshot, String s)
                {

                }
            });

            
            //adapter.clear();
            //RefeshList();
        }


    }


    private void RefeshList ()
    {

        pd.setMessage("Loading...");
        pd.show();

        String url = "https://androidchatapp-6140a.firebaseio.com/users/" + UserDetails.username + "/ListFriend.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ListFriend.this);
        rQueue.add(request);

    }



}