package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/28/2016.
 */

public class RegisterActivity extends Activity{
    @BindView(R.id.edt_user_first_name)
    EditText edtFirstName;
    @BindView(R.id.edt_user_last_name)
    EditText edtLastName;
    @BindView(R.id.edt_user_username)
    EditText edtUserName;
    @BindView(R.id.edt_user_email)
    EditText edtEmail;
    @BindView(R.id.edt_user_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.btn_register_done)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
