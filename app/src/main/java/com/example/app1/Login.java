package com.example.app1;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app1.data.DBManager;

import java.util.Objects;

public class Login extends AppCompatActivity {
    static TextView tvRegister;
    static EditText userName;
    static EditText password;
    static Button tvLogin;
    static DBManager dbManager;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userName = (EditText) findViewById(R.id.login_userName);
        password = (EditText) findViewById(R.id.login_password);
        dbManager = new DBManager(this);
        context = getApplicationContext();
        tvRegister = (TextView) findViewById(R.id.txt_register);
        tvRegister.setOnClickListener(new View.OnClickListener() { //cái này đang bị NullPointerException
            @Override
            public void onClick(View v) {
                Intent activitymoi=new Intent(Login.this, Register.class);
                startActivity(activitymoi);
            }
        });
        tvLogin = (Button) findViewById(R.id.btn_Login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(Objects.equals(userName.getText().toString(),"") || Objects.equals(password.getText().toString(),"")){
                    Toast.makeText(context,"User_name password required", Toast.LENGTH_LONG).show();
                }else{
                    int user_id = dbManager.checkLogin(userName.getText().toString(),password.getText().toString());
                    if(user_id == -1){
                        Toast.makeText(context,"Invalid user_name and password", Toast.LENGTH_LONG).show();
                    }else{
                        Intent activitymoi=new Intent(Login.this, UserInfo.class);
                        activitymoi.putExtra("user_id",user_id);
                        Log.d("user_id_login", String.valueOf(user_id));
                        startActivity(activitymoi);
                    }
                }
            }
        });
    }
}
