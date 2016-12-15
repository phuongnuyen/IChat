package com.example.trisinh.androidchatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        //đường dẫn tới CSDL firebase
        //2 người dùng A - B chat với nhau, (A là you, B là ng chat with)
        //reference1 là link CSDL cho you
        //reference2 là CSDL chat (lich su chat) cho nguoi dung B

        reference1 = new Firebase("https://androidchatapp-6140a.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://androidchatapp-6140a.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        //event click vào sendButton để gửi tin nhắn
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                //nếu khung tin nhắn trống thì không cần gửi
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    //gán cấu trúc vào map
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);

                    //up lên CSDL firebase
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);

                    messageArea.setText("");
                }
            }
        });

        //event nếu có 1 child (dữ liệu) được add lên trên database theo đường dẫn reference1
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //dataSnapshot lấy dữ liệu từ trên database về
                //dữ liệu được lấy về theo kiểu dữ liệu map (giống như table có hàng title và value
                Map map = dataSnapshot.getValue(Map.class);

                //lấy các dữ liệu ấy ra khỏi map
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
                if(userName.equals(UserDetails.username)){
                    //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
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
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

}
