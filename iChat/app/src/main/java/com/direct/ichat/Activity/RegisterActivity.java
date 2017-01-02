package com.direct.ichat.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.ichat.R;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phuong Nguyen Lan on 12/28/2016.
 */

public class RegisterActivity extends Activity{
    @BindView(R.id.btn_register_done)
    TextView btnRegister;
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


    String username;
    String password;
    String confPass;
    Context mContext;
    private boolean flagComplete = false;


    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
                if (flagComplete)
                {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //???
                    startActivity(intent);
                }
            }
        });
    }


    private void Register () {
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        confPass = edtConfirmPassword.getText().toString();

        if(username.equals("")){
            edtUsername.setError("can't be blank");
        }
        else if(password.equals("")){
            edtPassword.setError("can't be blank");
        }
        else if(!username.matches("[A-Za-z0-9]+")){ //kiểm tra có phải ký tự thường không
            edtUsername.setError("only alphabet or number allowed");
        }
        else if(username.length()<5){//kiểm tra username password phải có ít nhất 5 ký tự
            edtUsername.setError("at least 5 characters long");
        }
        else if(password.length()<5){
            edtPassword.setError("at least 5 characters long");
        }
        else if (confPass.length() < 5) {
            edtConfirmPassword.setError("at least 5 characters long");
        }
        else if (!password.equals(confPass)) {
            edtConfirmPassword.setError("wrong password");
        }
        else {
            //hiện bảng đang loading á
            final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
            pd.setMessage("Loading...");
            pd.show();

            //link url chứa CSDL
            String url = "https://androidchatapp-6140a.firebaseio.com/users.json";

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    Firebase.setAndroidContext(mContext);
                    Firebase reference = new Firebase("https://androidchatapp-6140a.firebaseio.com/users");

                    if(s.equals("null")) { //nếu child user chưa có
                        reference.child(username).child("password").setValue(password);
                        reference.child(username).child("FirstName").setValue(edtFirstName.getText().toString());
                        reference.child(username).child("LastName").setValue(edtLastName.getText().toString());
                        reference.child(username).child("Email").setValue(edtEmail.getText().toString());


                        Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                        flagComplete = true;

                    }
                    else {
                        try {
                            JSONObject obj = new JSONObject(s);

                            if (!obj.has(username)) {


                                reference.child(username).child("password").setValue(password);
                                reference.child(username).child("FirstName").setValue(edtFirstName.getText().toString());
                                reference.child(username).child("LastName").setValue(edtLastName.getText().toString());
                                reference.child(username).child("Email").setValue(edtEmail.getText().toString());


                                Toast.makeText(RegisterActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                flagComplete = true;
                            } else {
                                //Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                                edtUsername.setError("Username has already exist");
                                tvErrorMessage.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    pd.dismiss();
                }

            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError );
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
            rQueue.add(request);
        }
    }


}
