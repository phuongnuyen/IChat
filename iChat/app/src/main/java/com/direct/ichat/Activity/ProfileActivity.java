package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.direct.ichat.Adapter.OtherUserAdapter;
import com.direct.ichat.Fagment.SettingFragment;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Phuong Nguyen Lan on 12/29/2016.
 */

public class ProfileActivity extends Activity implements View.OnClickListener{
    public static final int FRIEND_PROFILE = 0;
    public static final int USER_OWN_PROFILE = 1;
    public static final int OTHER_PROFILE = 2;
    private int type = FRIEND_PROFILE;

    @BindView(R.id.btn_profile_add_friend)
    TextView btnAddFriend;
    @BindView(R.id.btn_profile_message)
    ImageButton btnMessage;
    @BindView(R.id.btn_profile_phone)
    ImageButton btnPhone;
    @BindView(R.id.btn_profile_remove_friend)
    ImageButton btnRemoveFriend;
    @BindView(R.id.btn_profile_timeline)
    ImageButton btnTimeline;
    @BindView(R.id.iv_profile_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.ln_profile_group_btn)
    LinearLayout lnGroupBtn;
    @BindView(R.id.tv_profile_adress)
    TextView tvAdress;
    @BindView(R.id.tv_profile_age)
    TextView tvAge;
    @BindView(R.id.tv_profile_name)
    TextView tvName;
    @BindView(R.id.tv_profile_email)
    TextView tvEmail;
    @BindView(R.id.tv_profile_gender)
    TextView tvGender;
    @BindView(R.id.tv_profile_onion_friends)
    TextView tvOnionFriendNumber;
    @BindView(R.id.tv_profile_phone_number)
    TextView tvPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        if (ExtrasDataFromAdapter() == false)
            ExtrasDataFromUserSetting();

        if (type == FRIEND_PROFILE){
            btnAddFriend.setVisibility(View.GONE);
            lnGroupBtn.setVisibility(View.VISIBLE);
        }
        if (type == OTHER_PROFILE){
            btnAddFriend.setVisibility(View.VISIBLE);
            lnGroupBtn.setVisibility(View.GONE);
        }
        if (type == USER_OWN_PROFILE){
            btnAddFriend.setVisibility(View.GONE);
            lnGroupBtn.setVisibility(View.GONE);
        }

        btnMessage.setOnClickListener(this);
    }

    private boolean ExtrasDataFromUserSetting(){
        User user = (User)getIntent().getSerializableExtra(SettingFragment.KEY_USER_OWN_PROFILE);
        if (user == null)
            return false;

        tvName.setText(user.GetName());
        tvEmail.setText(user.email);
        tvAge.setText(String.valueOf(user.age));
        tvGender.setText(user.gender);
        tvAdress.setText(user.address);
        tvPhoneNumber.setText(user.phoneNumber);

        this.type = USER_OWN_PROFILE;
        return true;
    }

    private boolean ExtrasDataFromAdapter(){
        User user = (User)getIntent().getSerializableExtra(OtherUserAdapter.KEY_USER);
        if (user == null)
            return false;

        tvName.setText(user.GetName());
        tvEmail.setText(user.email);
        tvAge.setText(String.valueOf(user.age));
        tvGender.setText(user.gender);
        tvAdress.setText(user.address);
        tvPhoneNumber.setText(user.phoneNumber);

        int adapterType = (int)getIntent().getSerializableExtra(OtherUserAdapter.KEY_TYPE);
        if (adapterType == OtherUserAdapter.FRIEND_LIST){
            this.type = FRIEND_PROFILE;
        } else {
            this.type = OTHER_PROFILE;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_profile_message:
                Intent intent = new Intent(this, ChatBoxActivity.class);
                startActivity(intent);
                break;
        }
    }
}
