package com.direct.ichat.Fagment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.direct.ichat.Activity.ChangePasswordActivity;
import com.direct.ichat.Activity.ProfileActivity;
import com.direct.ichat.Activity.UserDetails;
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

public class SettingFragment extends Fragment implements View.OnClickListener{
    public static final String KEY_USER_OWN_PROFILE = "userOwn";

    private Context mContext;
    private User mUser;

    //~~~~~~~~~~~~~~~
    //Firebase storage
    FirebaseStorage storage;


    @BindView(R.id.iv_setting_user_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_setting_name)
    TextView tvName;
    @BindView(R.id.tv_setting_username)
    TextView tvUsername;
    @BindView(R.id.btn_setting_view_profile)
    TextView btnViewProfile;
    @BindView(R.id.btn_setting_change_password)
    TextView btnChangePassword;
    @BindView(R.id.btn_setting_logout)
    TextView btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        ButterKnife.bind(this, view);
        mContext = this.getContext();

        //firebase storage
        storage = FirebaseStorage.getInstance();

        //dummy mUser --- will change with passing data
        //mUser = new User("abc", "101", "abcUsernam", "@email.com");
        mUser = UserDetails.user;


        SetupView();


        btnViewProfile.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        return view;
    }

    private void SetupView(){
        tvName.setText(mUser.GetName());
        tvUsername.setText(mUser.userName);

        //~~~~~~~~~~~~~~~~~~~~~test avatar

        if(UserDetails.user.strAvatarPath.equals(""))
        {
            //to do something in here;
        }
        else
        {
            StorageReference storageRef = storage.getReferenceFromUrl(UserDetails.user.strAvatarPath);



            Glide.with(mContext)
                    .using(new FirebaseImageLoader())
                    .load(storageRef)
                    .into(ivAvatar);

        }


        //~~~~~~~~~~~~~~~~~~~~~~
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_setting_view_profile:
                Intent intent = new Intent(mContext, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_USER_OWN_PROFILE, mUser);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;

            case R.id.btn_setting_change_password:

                break;

            case R.id.btn_setting_logout:
                getActivity().finish();
                System.exit(0);
                break;
        }
    }
}
