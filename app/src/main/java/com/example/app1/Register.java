package com.example.app1;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app1.data.DBManager;
import com.example.app1.model.Users;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText edit_userName;
    EditText edit_passWord;
    EditText edit_confirmPassWord;
    EditText edit_address;
    EditText edit_phoneNumber;
    Button btnSave;
    CheckBox chkbox;
    public static final String TAG = "Tag";
    private List<Users> Users;
    static DBManager dbManager;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbManager = Login.dbManager;
        btnSave = (Button) findViewById(R.id.btn_register);
        edit_userName = (EditText) findViewById(R.id.userName);
        edit_passWord = (EditText) findViewById(R.id.password);
        edit_confirmPassWord = (EditText) findViewById(R.id.confirmPassword);
        edit_address = (EditText) findViewById(R.id.address);
        edit_phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        chkbox = (CheckBox) findViewById(R.id.check_agree);
        context = getApplicationContext();

        btnSave.setOnClickListener(new View.OnClickListener() { //cái này đang bị NullPointerException
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override


            public void onClick(View v) {
                String edit_userName_txt = edit_userName.getText().toString();
                String edit_passWord_txt = edit_passWord.getText().toString();
                String edit_confirmPassWord_txt = edit_confirmPassWord.getText().toString();
                String edit_phone_txt = edit_phoneNumber.getText().toString();
                Users user = createUser();
                if(user!=null){
                    if(Objects.equals(edit_userName_txt,"") || Objects.equals(edit_passWord_txt,"") || Objects.equals(edit_confirmPassWord_txt,"") ){
                        Toast.makeText(context,"User_name password required", Toast.LENGTH_LONG).show();
                    }else if(!Objects.equals(edit_passWord_txt,edit_confirmPassWord_txt)){
                        Toast.makeText(context,"Password is not confirmed", Toast.LENGTH_LONG).show();
                    }else if(chkbox.isChecked() == false){
                        Toast.makeText(context,"You have to agree all the terms", Toast.LENGTH_LONG).show();
                    }else if(!Pattern.matches("[0-9]+", edit_phone_txt)){
                        Toast.makeText(context,"Invalid phone number", Toast.LENGTH_LONG).show();
                    }
                    else{
                        int user_id_created = dbManager.addUser(user);
                        if(user_id_created!=-1){
                            Intent activitymoi=new Intent(Register.this, UserInfo.class);
                            activitymoi.putExtra("user_id",user_id_created);
                            Log.d("user_id_register", String.valueOf(user_id_created));
                            startActivity(activitymoi);
                        }
                    }
                }
            }
        });
    }
    private Users createUser(){
        String userName = String.valueOf(edit_userName.getText());
        String passWord = edit_passWord.getText().toString();
        String confirmPassword = edit_confirmPassWord.getText().toString();
        String address = edit_address.getText().toString();
        String phoneNumber = edit_phoneNumber.getText().toString();
        String create_at =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Users user = new Users(1,userName,passWord,address,phoneNumber,create_at);
        return user;
    }
}
