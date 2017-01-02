package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.ichat.Adapter.References.ChatMessagesAdapter;
import com.direct.ichat.Model.ChatMessage;
import com.direct.ichat.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/28/2016.
 */

public class ChatBoxActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ChatActivity";
    private static final int RC_PHOTO_PICKER = 1;
    private String username;
    private ChatMessagesAdapter adapter;
    List<ChatMessage> listMessage;


    //Sinh làm
    Firebase reference1, reference2;
    FirebaseStorage storage;
    StorageMetadata metadata;
    UploadTask uploadTask;

    private static int RESULT_LOAD_IMG = 1;


    @BindView(R.id.btn_chat_send)
    RelativeLayout sendBtn;
    @BindView(R.id.edit_chat_inbox)
    EditText messageTxt;
    @BindView(R.id.rcv_chat_box)
    RecyclerView rcvMessageBox;
    @BindView(R.id.btn_chat_upload_file)
    RelativeLayout btnUploadFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_box);
        ButterKnife.bind(this);

        listMessage = new ArrayList<>();
//        listMessage.add(new ChatMessage("09:00", "hello", 1));
//        listMessage.add(new ChatMessage("10:30", "hello", 1));
//        listMessage.add(new ChatMessage("10:30", "wqtbadznd", 0));
//        listMessage.add(new ChatMessage("11:30", "1111", 1));
//        listMessage.add(new ChatMessage("11:31", "A", 0));
//        listMessage.add(new ChatMessage("11:31", "asc", 0));
//        listMessage.add(new ChatMessage("13:00", "szgfd", 0));
//        listMessage.add(new ChatMessage("13:01", ":)", 1));


        adapter = new ChatMessagesAdapter(listMessage);
        rcvMessageBox.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.smoothScrollToPosition(adapter.getItemCount());
        rcvMessageBox.setLayoutManager(linearLayoutManager);
        rcvMessageBox.setAdapter(adapter);
        rcvMessageBox.smoothScrollToPosition(adapter.getItemCount());

        storage = FirebaseStorage.getInstance();

        Firebase.setAndroidContext(this);
        //đường dẫn tới CSDL firebase
        //2 người dùng A - B chat với nhau, (A là you, B là ng chat with)
        //reference1 là link CSDL cho you
        //reference2 là CSDL chat (lich su chat) cho nguoi dung B

        reference1 = new Firebase("https://androidchatapp-6140a.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.userChatWith.userName);
        reference2 = new Firebase("https://androidchatapp-6140a.firebaseio.com/messages/" + UserDetails.userChatWith.userName + "_" + UserDetails.username);


        btnUploadFile.setOnClickListener(this);
        sendBtn.setOnClickListener(this);




        //event nếu có 1 child (dữ liệu) được add lên trên database theo đường dẫn reference1
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ShowMessage(dataSnapshot);
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



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chat_send:

                String messageText = messageTxt.getText().toString();

                //nếu khung tin nhắn trống thì không cần gửi
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    //gán cấu trúc vào map
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);

                    //up lên CSDL firebase
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);

                    messageTxt.setText("");
                }

                break;

            case R.id.btn_chat_upload_file:
                OpenGallery();
                break;
        }
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    protected void ShowMessage(DataSnapshot dataSnapshot) {
        //dataSnapshot lấy dữ liệu từ trên database về
        //dữ liệu được lấy về theo kiểu dữ liệu map (giống như table có hàng title và value
        Map map = dataSnapshot.getValue(Map.class);

        //lấy các dữ liệu ấy ra khỏi map
        String message = map.get("message").toString();
        String userName = map.get("user").toString();

        if (message.length() > 7)
            if (!message.substring(0, 6).equals("@I@M@A")) {
                //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
                if (userName.equals(UserDetails.username)) {
                    //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                    addMessageBox(message, 1);
                } else {
                    //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                    addMessageBox(message, 2);
                }
            } else {
                //trong đây sẽ gọi imageview để thể hiện ra cửa sổ chat
                //chưa có imageiview nên tạm thời in ra String StorageLocation v
                //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
                System.out.println("Test");
                if (userName.equals(UserDetails.username)) {
                    //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                    addMessageBox(message.substring(6), 1);
                    addMessageBox(message, 1);
                } else {
                    //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                    System.out.println(message);
                    System.out.println(UserDetails.userChatWith.userName);
                    addMessageBox(message, 2);
                }
            }
        else {
            //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
            if (userName.equals(UserDetails.username)) {
                //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                addMessageBox(message, 1);
            } else {
                //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                addMessageBox(message, 2);
            }
        }


    }

    public void addMessageBox(String message, int type) {

        ChatMessage chat;

        if (type == 1)
            chat = new ChatMessage(UserDetails.username, message, 0);
        else
            chat = new ChatMessage(UserDetails.userChatWith.userName, message, 1);


        adapter.AddMessage(chat);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String ImagePath;
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                ImagePath = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                /*imageView.setImageBitmap(BitmapFactory
                        .decodeFile(ImagePath));*/


                //textView.setText(ImagePath);

                UploadImage(ImagePath);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    private void OpenGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    //dùng để upload hình vào storage
    private void UploadImage(String StrPath) {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://androidchatapp-6140a.appspot.com/");

        Uri file = Uri.fromFile(new File(StrPath));

        // Create the file metadata
        metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        StorageReference fileRef = storageRef.child(UserDetails.username + "/" + file.getLastPathSegment());

        final String StorageLocation = fileRef.toString();

        uploadTask = fileRef.putFile(file);//,metadata);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                //Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                String messageText = new String("@I@M@A" + StorageLocation);

                Map<String, String> map = new HashMap<String, String>();
                //gán cấu trúc vào map
                map.put("message", messageText);
                map.put("user", UserDetails.username);

                //up lên CSDL firebase
                reference1.push().setValue(map);
                reference2.push().setValue(map);


                messageTxt.setText("");

            }
        });


    }

}
