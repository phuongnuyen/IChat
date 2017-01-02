package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;
import com.firebase.client.Firebase;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/30/2016.
 */

public class SettingProfileActivity extends Activity implements View.OnClickListener{


    @BindView(R.id.tv_profile_name)
    TextView tvProfileName;
    @BindView(R.id.tv_profile_email)
    TextView tvProfileEmail;
    @BindView(R.id.edt_register_first_name)
    EditText edtRegisterFirstName;
    @BindView(R.id.edt_register_last_name)
    EditText edtRegisterLastName;
    @BindView(R.id.edt_register_username) // đây là edt của address không phải username
    EditText edtRegisterAddress;
    @BindView(R.id.edt_register_age)
    EditText edtRegisterAge;
    @BindView(R.id.edt_register_gender)
    EditText edtRegisterGender;
    @BindView(R.id.edt_register_phone_number)
    EditText edtPhoneNumber;
    @BindView(R.id.edt_register_email)
    EditText edtEmail;
    @BindView(R.id.iv_register_avatar)
    ImageView ivProfileAvatar;
    @BindView(R.id.ib_register_avatar)
    ImageButton ibAvatar;
    @BindView(R.id.iv_register_cancel)
    RelativeLayout ivCancel;
    @BindView(R.id.iv_register_done)
    RelativeLayout ivDone;




    private static int RESULT_LOAD_IMG = 1;
    //storage
    //Firebase storage
    FirebaseStorage storage;
    UploadTask uploadTask;
    String strAvatarPath = "";
    boolean flagCompleteUpload = true;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting_profile);
        ButterKnife.bind(this);

        //firebase storage
        storage = FirebaseStorage.getInstance();

        Firebase.setAndroidContext(this);

        setView();

        ibAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                flagCompleteUpload = false;
                OpenGallery();

            }

        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(flagCompleteUpload) {
                    UpdateInfo();
                    finish();
                } else {
                    Toast.makeText(SettingProfileActivity.this, "Please wait for a minute", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    private void setView()
    {
        User currentUser = UserDetails.user;
        tvProfileName.setText(currentUser.GetName());
        tvProfileEmail.setText(currentUser.email);
        edtRegisterFirstName.setText(currentUser.firstName);
        edtRegisterLastName.setText(currentUser.lastName);
        edtRegisterAddress.setText(currentUser.address);
        edtRegisterAge.setText(String.valueOf(currentUser.age));
        edtRegisterGender.setText(currentUser.gender);
        edtPhoneNumber.setText(currentUser.phoneNumber);
        edtEmail.setText(currentUser.email);

        if(currentUser.strAvatarPath.equals(""))
        {
            //to do something in here;
        }
        else
        {
            StorageReference storageRef = storage.getReferenceFromUrl(currentUser.strAvatarPath);



            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(storageRef)
                    .into(ivProfileAvatar);

        }

    }


    //Hàm chức năng phục vụ cho việc nhấn vào ImageButton

    private void OpenGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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

//                Set the Image in ImageView after decoding the String
//                ivProfileAvatar.setImageBitmap(BitmapFactory
//                        .decodeFile(ImagePath));

                //textView.setText(ImagePath);

                UploadImage(ImagePath);

            } else {
                flagCompleteUpload = true;
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    //dùng để upload hình vào storage
    private void UploadImage(String StrPath) {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://androidchatapp-6140a.appspot.com/");

        Uri file = Uri.fromFile(new File(StrPath));



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
                flagCompleteUpload = true;
                strAvatarPath = StorageLocation;
                System.out.println("Successfully");

                StorageReference storageRef = storage.getReferenceFromUrl(StorageLocation);


                Glide.with(SettingProfileActivity.this)
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(ivProfileAvatar);
            }
        });


    }
    //~~~~~~~~~~~~~~~~~~~~~~~~

    //Các hàm dùng để cập nhật lại thông tin người dùng
    private void UpdateInfo()
    {
        //Chèn thêm trên firebase
        DatabaseReference refUser;
        FirebaseDatabase database;

        database = FirebaseDatabase.getInstance();

        //add friend cho mình
        refUser = database.getReferenceFromUrl("https://androidchatapp-6140a.firebaseio.com/users")
                .child(UserDetails.username);

        refUser.child("FirstName").setValue(edtRegisterFirstName.getText().toString());
        refUser.child("LastName").setValue(edtRegisterLastName.getText().toString());
        refUser.child("Address").setValue(edtRegisterAddress.getText().toString());
        refUser.child("Age").setValue(edtRegisterAge.getText().toString());
        refUser.child("Gender").setValue(edtRegisterGender.getText().toString());
        refUser.child("PhoneNumber").setValue(edtPhoneNumber.getText().toString());
        refUser.child("Email").setValue(edtEmail.getText().toString());

        User userInfo = new User(UserDetails.username,
                edtRegisterFirstName.getText().toString(),
                edtRegisterLastName.getText().toString(),
                edtEmail.getText().toString(),
                edtRegisterAge.getText().toString(),
                edtRegisterAddress.getText().toString(),
                edtRegisterGender.getText().toString(),
                edtPhoneNumber.getText().toString());

        if(!strAvatarPath.equals("")) {
            refUser.child("AvatarPath").setValue(strAvatarPath);
            UserDetails.user.SetAvatar(strAvatarPath);
        }


    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~

}
