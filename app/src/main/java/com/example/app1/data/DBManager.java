package com.example.app1.data;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.app1.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import androidx.annotation.RequiresApi;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users_manager";
    private static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "username";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phone";
    private static final String CREATED_AT = "creatd_at";
    private Context context;

    private String SQLQuery = "CREATE TABLE "+TABLE_NAME+"("+
            ID+" integer primary key AUTOINCREMENT,"+
            PASSWORD+" text,"+
            USER_NAME+ " text unique,"+
            ADDRESS+ " text,"+
            PHONE_NUMBER+ " text,"+
            CREATED_AT+ " datetime default current_timestamp"
            +")";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        Log.i("DB", "DB Created");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
        Toast.makeText(context,"Create successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        Toast.makeText(context,"Drop successfully", Toast.LENGTH_LONG).show();
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int addUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getUserName());
        contentValues.put(PASSWORD, user.getPassWord());
        contentValues.put(ADDRESS, user.getAddress());
        contentValues.put(PHONE_NUMBER, user.getPhoneNumber());
        contentValues.put(CREATED_AT, user.getCreate_at().toString());

        String query = "SELECT COUNT(*) AS NUM FROM "+TABLE_NAME+" WHERE "+USER_NAME+" = '"+user.getUserName()+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(Objects.equals(cursor.getString(cursor.getColumnIndex("NUM")),"1")){
            Toast.makeText(context,"Exist user_name", Toast.LENGTH_LONG).show();
            cursor.close();
            db.close();
            return -1;
        }else{
            db.insert(TABLE_NAME, null, contentValues);
            Toast.makeText(context,"Register successfully", Toast.LENGTH_LONG).show();
            query = "SELECT MAX(ID) AS MAX_ID FROM  users";
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("MAX_ID")));
            cursor.close();
            db.close();
            return id;
        }

//        List<Users> list_user = this.getAllUsers();
//        for (Users users : list_user) {
//            Log.d("User",users.toString());
//        }
//        Log.d("DB",SQLQuery );
    }

    public Users getUserByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        USER_NAME,PASSWORD, ADDRESS,PHONE_NUMBER,CREATED_AT }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(id,cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        db.close();
        return user;
    }

    public int Update(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME,user.getUserName());

        return db.update(TABLE_NAME,values,ID +"=?",new String[] { String.valueOf(user.getID())});
    }

    public List<Users> getAllUsers() {
        List<Users> listUsers = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setID(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                user.setPassWord(cursor.getString(2));
                user.setAddress(cursor.getString(3));
                user.setPhoneNumber(cursor.getString(4));
                user.setCreate_at(cursor.getString(5));
                listUsers.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listUsers;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int deleteAll() {
        String query = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int checkLogin(String user_name, String password){
        String query = "SELECT *,COUNT(*) AS NUM FROM " + TABLE_NAME + " WHERE " + USER_NAME+"='"+user_name+"' AND "+PASSWORD+"='"+password+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(Objects.equals(cursor.getString(cursor.getColumnIndex("NUM")),"1")){
            return Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
        }else{
            return  -1;
        }

    }
}
