package com.direct.ichat.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.direct.ichat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/20/2016.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.edt_username)
    EditText edtUserName;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    RelativeLayout btnRegister;


    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if (edtUserName.getText().equals("abc")
                    && edtPassword.getText().equals("abc")){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
