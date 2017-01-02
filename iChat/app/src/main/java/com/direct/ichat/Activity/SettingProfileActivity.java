package com.direct.ichat.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    @BindView(R.id.btn_reject)
    RelativeLayout btnReject;
    @BindView(R.id.btn_accept)
    RelativeLayout btnAccept;


    //~~~~~~~~~~~~~~~
    //Firebase storage
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting_profile);
        ButterKnife.bind(this);

        //firebase storage
        storage = FirebaseStorage.getInstance();

        setView();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reject:
                onBackPressed();
                break;

            case R.id.btn_accept:
                break;
        }

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

}
