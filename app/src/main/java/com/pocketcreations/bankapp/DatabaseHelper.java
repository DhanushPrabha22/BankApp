package com.pocketcreations.bankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bank.db";
    public static final String TABLE1_NAME = "user_table";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "PHOTO";
    public static final String COL_4 = "AMOUNT";
    public static final String TABLE2_NAME = "trans_table";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PHOTO INTEGER, AMOUNT INTEGER)");
        db.execSQL("create table " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SENDER TEXT, RECIPIENT TEXT, AMOUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE1_NAME);
        db.execSQL("drop table if exists "+TABLE2_NAME);
        onCreate(db);
    }
    public void insertTable1(String name, String email, Integer photo, Integer amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,email);
        contentValues.put(COL_3,photo);
        contentValues.put(COL_4,amount);
        db.insert(TABLE1_NAME,null,contentValues);
    }

    public void insertTable2(String sender, String recipient, int amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SENDER",sender);
        contentValues.put("RECIPIENT",recipient);
        contentValues.put("AMOUNT",amt);
        db.insert(TABLE2_NAME,null,contentValues);
    }

    public Cursor getTable1(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME+" where ID="+id,null);
        return res;
    }

    public Cursor getTable2All(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE2_NAME,null);
        return res;
    }

    public void updateTable1(String name,int amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,amt);
        db.update(TABLE1_NAME,contentValues,"NAME = ?",new String[]{ name });
    }

    public Cursor balance(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE1_NAME+" where NAME="+user,null);
        return res;
    }
}
