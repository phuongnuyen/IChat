package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    public static final int WAITING_USER_PROFILE = 3;
    public static final String KEY_USER = "mUser";
    
    private User mUser;
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
    @BindView(R.id.btn_profile_edit)
    TextView btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        if (ExtrasDataFromAdapter() == false)
            ExtrasDataFromUserSetting();

        switch (type){
            case FRIEND_PROFILE:
                btnAddFriend.setVisibility(View.GONE);
                lnGroupBtn.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                break;

            case OTHER_PROFILE:
                btnAddFriend.setVisibility(View.VISIBLE);
                lnGroupBtn.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                break;

            case  USER_OWN_PROFILE:
                btnAddFriend.setVisibility(View.GONE);
                lnGroupBtn.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                break;

            case WAITING_USER_PROFILE:
                btnAddFriend.setVisibility(View.GONE);
                lnGroupBtn.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                break;
        }

        btnMessage.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnTimeline.setOnClickListener(this);
        btnRemoveFriend.setOnClickListener(this);
        btnAddFriend.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }

    private boolean ExtrasDataFromUserSetting(){
        mUser = (User) getIntent().getSerializableExtra(SettingFragment.KEY_USER_OWN_PROFILE);
        if (mUser == null)
            return false;

        tvName.setText(mUser.GetName());
        tvEmail.setText(mUser.email);
        tvAge.setText(String.valueOf(mUser.age));
        tvGender.setText(mUser.gender);
        tvAdress.setText(mUser.address);
        tvPhoneNumber.setText(mUser.phoneNumber);

        this.type = USER_OWN_PROFILE;
        return true;
    }

    private boolean ExtrasDataFromAdapter(){
        mUser = (User)getIntent().getSerializableExtra(OtherUserAdapter.KEY_USER);
        if (mUser == null)
            return false;

        tvName.setText(mUser.GetName());
        tvEmail.setText(mUser.email);
        tvAge.setText(String.valueOf(mUser.age));
        tvGender.setText(mUser.gender);
        tvAdress.setText(mUser.address);
        tvPhoneNumber.setText(mUser.phoneNumber);

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
        Intent intent;
        switch (view.getId()){
            case R.id.btn_profile_message:
                UserDetails.userChatWith = mUser;
                intent = new Intent(this, ChatBoxActivity.class);
                startActivity(intent);
                return;

            case R.id.btn_profile_phone:
                break;

            case R.id.btn_profile_timeline:
                break;

            case R.id.btn_profile_remove_friend:
                break;

            case R.id.btn_profile_add_friend:
                break;

            case R.id.btn_profile_edit:
                intent = new Intent(this, SettingProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(this.KEY_USER, mUser);
                intent.putExtras(bundle);
                this.startActivity(intent);
                return;
        }
    }
}
