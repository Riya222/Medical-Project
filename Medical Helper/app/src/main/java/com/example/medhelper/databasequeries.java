package com.example.medhelper;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databasequeries extends SQLiteOpenHelper {
    public databasequeries(Activity a) {
        super(a, "prodb", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




    public long userInsert( String email,  String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table users(ID INTEGER PRIMARY KEY AUTOINCREMENT,email varchar(100),mobile varchar(100),password varchar(100), UNIQUE (email))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("mobile", mobile);
        cv.put("password", password);
        try {
            long ab = db.insert("users", null, cv);
            db.close();
            return ab;
        }
        catch(Exception ex1){
            long ab=0;
            Log.e("Ex: ",ex1.toString());
            return ab;
        }
    }

    public String checkUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table users(ID INTEGER PRIMARY KEY AUTOINCREMENT,email varchar(100),mobile varchar(100),password varchar(100), UNIQUE (email))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        String sqlquery="SELECT * FROM users WHERE TRIM(email) = '"+email.trim()+"' and TRIM(password) = '"+password.trim()+"'";
        Log.e("==>SQLQUERY==>",sqlquery);
        Cursor cursor = db.rawQuery(sqlquery,null);
        int CursorCount= cursor.getCount();
        Log.e("==>COUNT==>","Val: "+CursorCount);
        if(cursor.moveToFirst()) {
            return "user";
        }
        else {
            return "none";
        }

    }
}
