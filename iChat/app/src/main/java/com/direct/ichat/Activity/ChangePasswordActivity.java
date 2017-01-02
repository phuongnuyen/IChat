package com.direct.ichat.Activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/31/2016.
 */

public class ChangePasswordActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private User user;

    @BindView(R.id.edt_old_password)
    EditText edtOldPassword;
    @BindView(R.id.edt_new_password)
    EditText edtNewPassword;
    @BindView(R.id.edt_confirm_new_password)
    EditText edtConfirmNewPass;
    @BindView(R.id.tv_change_pass_error)
    TextView tvChangePassError;
    @BindView(R.id.btn_reject)
    RelativeLayout btnReject;
    @BindView(R.id.btn_accept)
    RelativeLayout btnAccept;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        mContext = this;

        //test
        user = new User("abc", "a", "b", "c@gmail.com");

        btnReject.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reject:
                onBackPressed();
                break;

            case R.id.btn_accept:
                //if (edtOldPassword.getText().equals())
                break;
        }
    }
}
