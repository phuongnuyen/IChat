package com.example.trisinh.androidchatapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    ImageView sendButton;
    Button imageButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;

    //storage
    //Firebase storage
    FirebaseStorage storage;
    StorageMetadata metadata;
    UploadTask uploadTask;

    private static int RESULT_LOAD_IMG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        imageButton = (Button)findViewById(R.id.imageButton);

        storage = FirebaseStorage.getInstance();

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

        imageButton.setOnClickListener ( new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }

        });


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

    protected void ShowMessage(DataSnapshot dataSnapshot)
    {
        //dataSnapshot lấy dữ liệu từ trên database về
        //dữ liệu được lấy về theo kiểu dữ liệu map (giống như table có hàng title và value
        Map map = dataSnapshot.getValue(Map.class);

        //lấy các dữ liệu ấy ra khỏi map
        String message = map.get("message").toString();
        String userName = map.get("user").toString();

        if (message.length() > 7)
            if (!message.substring(0,6).equals("@I@M@A")) {
                //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
                if (userName.equals(UserDetails.username)) {
                    //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                }
            } else {
                //trong đây sẽ gọi imageview để thể hiện ra cửa sổ chat
                //chưa có imageiview nên tạm thời in ra String StorageLocation v
                //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
                System.out.println("Test");
                if (userName.equals(UserDetails.username)) {
                    //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                    addMessageBox("You:-\n" + message.substring(6), 1);
                } else {
                    //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                    addMessageBox(UserDetails.chatWith + ":-\n" + message.substring(6), 2);
                }
            }
        else
        {
            //kiểm tra nếu dữ liệu mới vừa được add lên đó có phải do A gửi lên hay kg
            if (userName.equals(UserDetails.username)) {
                //phải thì chọn hiện tin nhắn theo kiểu you gửi lên
                addMessageBox("You:-\n" + message, 1);
            } else {
                //không phải thì hiện tin nhắn theo kiểu ng chat cùng gửi lên
                addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
            }
        }


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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String ImagePath;
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

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



    private void OpenGallery ()
    {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    //dùng để upload hình vào storage
    private void UploadImage (String StrPath)
    {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://androidchatapp-6140a.appspot.com/");

        Uri file = Uri.fromFile(new File(StrPath));

        // Create the file metadata
        metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        StorageReference fileRef = storageRef.child(UserDetails.username + "/" +file.getLastPathSegment());

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

                String messageText = new String ("@I@M@A" + StorageLocation);

                Map<String, String> map = new HashMap<String, String>();
                //gán cấu trúc vào map
                map.put("message", messageText);
                map.put("user", UserDetails.username);

                //up lên CSDL firebase
                reference1.push().setValue(map);
                reference2.push().setValue(map);

                messageArea.setText("");

            }
        });


    }


}
