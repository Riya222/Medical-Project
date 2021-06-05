package com.example.crimeapp;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.os.Build.ID;

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

    public long doctorInsert( String email,  String mobile, String password, String hosp, String des) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table doctors(ID INTEGER PRIMARY KEY AUTOINCREMENT,email varchar(100),hosp varchar(100),desig varchar(100),mobile varchar(100),password varchar(100), UNIQUE (email))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("mobile", mobile);
        cv.put("password", password);
        cv.put("hosp", hosp);
        cv.put("desig", des);
        try {
            long ab = db.insert("doctors", null, cv);
            db.close();
            Log.e("<--Doc-->", "Inserted");
            return ab;
        }
        catch(Exception ex1){
            long ab=0;
            Log.e("Ex: ",ex1.toString());
            return ab;
        }
    }

    public long tabletInsert( String name,  String descr, String medtype, String medclr, String intake, String medtime, String dosagetype, String dosage, String interval, String balance, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table tablets(ID INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(100),descr varchar(100),medtype varchar(100),medclr varchar(100),intake varchar(100), medtime varchar(100), dosagetype varchar(100), dosage varchar(100), interval varchar(100), balance varchar(100), username varchar(100))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("descr", descr);
        cv.put("medtype", "medtype");
        cv.put("medclr", medclr);
        cv.put("intake", intake);
        cv.put("medtime", medtime);
        cv.put("dosagetype", dosagetype);
        cv.put("dosage", dosage);
        cv.put("interval", interval);
        cv.put("balance", balance);
        cv.put("username", username);
        try {
            long ab = db.insert("tablets", null, cv);
            db.close();
            return ab;
        }
        catch(Exception ex1){
            long ab=0;
            Log.e("Ex: ",ex1.toString());
            return ab;
        }
    }

    public long edittabletInsert( String idv, String name,  String descr, String medtype, String medclr, String intake, String medtime, String dosagetype, String dosage, String interval, String balance, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table tablets(ID INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(100),descr varchar(100),medtype varchar(100),medclr varchar(100),intake varchar(100), medtime varchar(100), dosagetype varchar(100), dosage varchar(100), interval varchar(100), balance varchar(100), username varchar(100))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("descr", descr);
        cv.put("medtype", "medtype");
        cv.put("medclr", medclr);
        cv.put("intake", intake);
        cv.put("medtime", medtime);
        cv.put("dosagetype", dosagetype);
        cv.put("dosage", dosage);
        cv.put("interval", interval);
        cv.put("balance", balance);
        try {

            Log.e("ID: ", idv);
            long ab = db.update("tablets",cv,"ID= ?",new String[]{idv});

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

    public long reminderInsert(String rem, String descr, String times) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table reminders(ID INTEGER PRIMARY KEY AUTOINCREMENT,rem varchar(100),descr varchar(100),times varchar(100), UNIQUE (rem))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        ContentValues cv = new ContentValues();
        cv.put("rem", rem);
        cv.put("descr", descr);
        cv.put("times", times);
        try {
            long ab = db.insert("reminders", null, cv);
            db.close();
            Log.e("<--Rem-->", "Inserted");
            return ab;
        }
        catch(Exception ex1){
            long ab=0;
            Log.e("Ex: ",ex1.toString());
            return ab;
        }
    }

    public long saveImage(String username, String encodedImage,String dtm) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table saved(ID INTEGER PRIMARY KEY AUTOINCREMENT,username varchar(100),img varchar(100),dtm varchar(100))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("img", encodedImage);
        cv.put("dtm",dtm);
        try {
            long ab = db.insert("saved", null, cv);
            db.close();
            Log.e("<--Rem-->", "Inserted");
            return ab;
        }
        catch(Exception ex1){
            long ab=0;
            Log.e("Ex: ",ex1.toString());
            return ab;
        }
    }

    public Cursor getDoctors() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table doctors(ID INTEGER PRIMARY KEY AUTOINCREMENT,email varchar(100),hosp varchar(100),desig varchar(100),mobile varchar(100),password varchar(100), UNIQUE (email))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        String sqlquery="SELECT * FROM doctors";
        Log.e("==>SQLQUERY==>",sqlquery);
        Cursor cursor = db.rawQuery(sqlquery,null);
        return cursor;
    }
public Cursor getimages(String usr){
    SQLiteDatabase db = this.getWritableDatabase();
    try {
        db.execSQL("create table saved(ID INTEGER PRIMARY KEY AUTOINCREMENT,username varchar(100),img varchar(10000000),dtm varchar(100))");
    }catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        String sqlquery="SELECT * FROM saved where username='"+usr+"'";
        Log.e("==>SQLQUERY==>",sqlquery);
        Cursor cursor = db.rawQuery(sqlquery,null);
        return cursor;

}
    public Cursor getTablets() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("create table tablets(ID INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(100),descr varchar(100),medtype varchar(100),medclr varchar(100),intake varchar(100), medtime varchar(100), dosagetype varchar(100), dosage varchar(100), interval varchar(100), balance varchar(100), username varchar(100))");

        }
        catch(Exception ex){
            Log.e("<--ERROR-->",ex.toString());
        }
        String sqlquery="SELECT * FROM tablets";
        Log.e("==>SQLQUERY==>",sqlquery);
        Cursor cursor = db.rawQuery(sqlquery,null);
        Log.e("cnt: ", ""+cursor.getCount());
        return cursor;
    }
}
