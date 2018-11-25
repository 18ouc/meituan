package com.example.lenovo_pc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "username text,"
            + "account text,"
            + "password text,"
            + "phonenumber integer,"
            + "shopname text,"
            + "shoplocation text,"
            + "shopphoto integer)";


    private static final String CREATE_SHOP = "create table Shop ("
            + "id integer primary key autoincrement, "
            + "shop_name text, "
            + "shop_location text,"
            + "account text)";

    private static final String CREATE_FOOD = "create table Food ("
            + "id integer primary key autoincrement, "
            + "food_name text, "
            + "food_price real,"
            + "food_photo integer,"
            + "shop_name text,"
            + "account text)";

    private static final String CREATE_MENU = "create table Menu ("
            + "id integer primary key autoincrement, "
            + "food_name text, "
            + "food_price real,"
            + "food_photo integer,"
            + "shop_name text,"
            + "account text)";



    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_SHOP);
        db.execSQL(CREATE_FOOD );
        db.execSQL(CREATE_MENU );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Shop");       //升级数据库 = 删掉可能存在的旧表 + 创建新表
        db.execSQL("drop table if exists Food");
        db.execSQL("drop table if exists Menu");
        onCreate(db);
    }
}
