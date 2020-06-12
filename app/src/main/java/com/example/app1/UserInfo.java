package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app1.data.DBManager;
import com.example.app1.model.Users;

public class UserInfo extends AppCompatActivity {
    TextView tv_Logout;
    TextView tv_createAt;
    TextView tv_phoneNumber;
    TextView tv_address;
    TextView tv_password;
    TextView tv_username;
    TextView tv_id;
    Users user;
    static DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.user_info);
            int value = extras.getInt("user_id");
            Log.d("user_id", String.valueOf(value));

            dbManager = new DBManager(this);
            Users user = dbManager.getUserByID(value);
            Log.d("user info login:",user.toString());
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_username = (TextView) findViewById(R.id.tv_username);
            tv_password = (TextView) findViewById(R.id.tv_password);
            tv_address = (TextView) findViewById(R.id.tv_address);
            tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);
            tv_createAt = (TextView) findViewById(R.id.tv_createAt);
            tv_Logout = (TextView) findViewById(R.id.tv_logout);

            tv_id.setText("ID:\n"+ String.valueOf(user.getID()));
            tv_username.setText("Username:"+user.getUserName());
            tv_password.setText("Password:"+user.getPassWord());
            tv_address.setText("Address:"+user.getAddress());
            tv_phoneNumber.setText("Phone number:"+user.getPhoneNumber());
            tv_createAt.setText("Created at:"+user.getCreate_at());

            tv_Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activitymoi=new Intent(UserInfo.this, Login.class);
                    startActivity(activitymoi);
                }
            });
        }
    }
}
