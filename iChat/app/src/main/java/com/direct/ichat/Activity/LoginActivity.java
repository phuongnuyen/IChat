package com.direct.ichat.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.direct.ichat.Model.User;
import com.direct.ichat.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    TextView btnRegister;

    String user, pass;

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

                Login();

                break;

            case R.id.btn_register:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }

    private void Login()
    {
        user = edtUserName.getText().toString();
        pass = edtPassword.getText().toString();

        if(user.equals("")){
            edtUserName.setError("can't be blank");
        }
        else if(pass.equals("")){
            edtPassword.setError("can't be blank");
        }
        else{
            String url = "https://androidchatapp-6140a.firebaseio.com/users.json";
            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loading...");
            pd.show();

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    if(s.equals("null")){
                        Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                    }
                    else{
                        try {
                            JSONObject obj = new JSONObject(s);

                            if(!obj.has(user)){
                                Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else if(obj.getJSONObject(user).getString("password").equals(pass)){
                                UserDetails.username = user;
                                UserDetails.password = pass;
                                UserDetails.obj = obj;

                                //tìm tất cả thông tin của người dùng vừa mới login vào
                                String firstName = obj.getJSONObject(user).getString("FirstName");
                                String lastName = obj.getJSONObject(user).getString("LastName");
                                String email = obj.getJSONObject(user).getString("Email");

                                User userInfo = new User(user, firstName, lastName, email);

                                UserDetails.user = userInfo;


                                Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainActivity);

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
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
                    System.out.println("" + volleyError);
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
            rQueue.add(request);
        }
    }


}
