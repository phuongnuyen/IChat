package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/28/2016.
 */

public class RegisterActivity extends Activity{
    @BindView(R.id.btn_register_done)
    TextView btnRegister;
    // them vao
    @BindView(R.id.edt_register_first_name)
    EditText edtFirstName;
    @BindView(R.id.edt_register_last_name)
    EditText edtLastName;
    @BindView(R.id.edt_register_username)
    EditText edtUsername;
    @BindView(R.id.edt_register_email)
    EditText edtEmail;
    @BindView(R.id.edt_register_password)
    EditText edtPassword;
    @BindView(R.id.edt_register_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.tv_register_error_message)
    TextView tvErrorMessage;



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
