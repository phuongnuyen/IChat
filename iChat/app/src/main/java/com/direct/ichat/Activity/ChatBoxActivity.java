package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.direct.ichat.Adapter.References.ChatMessagesAdapter;
import com.direct.ichat.Model.ChatMessage;
import com.direct.ichat.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/28/2016.
 */

public class ChatBoxActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "ChatActivity";
    private static final int RC_PHOTO_PICKER = 1;
    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private String username;

    private ChatMessagesAdapter adapter;
    List<ChatMessage> listMessage;

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
        setContentView(R.layout.activity_chat_box);
        ButterKnife.bind(this);

        listMessage = new ArrayList<>();
        listMessage.add(new ChatMessage("09:00", "hello", 1));
        listMessage.add(new ChatMessage("10:30", "hello", 1));
        listMessage.add(new ChatMessage("10:30", "wqtbadznd", 0));
        listMessage.add(new ChatMessage("11:30", "1111", 1));
        listMessage.add(new ChatMessage("11:31", "A", 0));
        listMessage.add(new ChatMessage("11:31", "asc", 0));
        listMessage.add(new ChatMessage("13:00", "szgfd", 0));
        listMessage.add(new ChatMessage("13:01", ":)", 1));


        adapter = new ChatMessagesAdapter(listMessage);
        rcvMessageBox.setHasFixedSize(true);
        rcvMessageBox.setLayoutManager(new LinearLayoutManager(this));
        rcvMessageBox.setAdapter(adapter);

        btnUploadFile.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //Recieved result from image picker
//        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
//            Uri selectedImageUri = data.getData();
//            // Get a reference to the location where we'll store our photos
//            storageRef = storage.getReference("chat_photos");
//            // Get a reference to store file at chat_photos/<FILENAME>
//            final StorageReference photoRef = storageRef.child(selectedImageUri.getLastPathSegment());
//            // Upload file to Firebase Storage
//            photoRef.putFile(selectedImageUri)
//                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // When the image has successfully uploaded, we get its download URL
//                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            // Send message with Image URL
//                            ChatMessage chat = new ChatMessage(downloadUrl.toString(), username);
//                            databaseRef.push().setValue(chat);
//                            messageTxt.setText("");
//                        }
//                    });
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_chat_send:
                ChatMessage chat = new ChatMessage(messageTxt.getText().toString(), username, 0);
                // Push the chat message to the database
                //databaseRef.push().setValue(chat);
                adapter.AddMessage(chat);
                messageTxt.setText("");

                break;

            case R.id.btn_chat_upload_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

                break;
        }
    }
}
