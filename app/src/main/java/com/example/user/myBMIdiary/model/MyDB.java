package com.example.user.myBMIdiary.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {
    public static SQLiteDatabase getMyDb(Context context) {
        //1. 取得或建立資料庫
        SQLiteDatabase mydb = context.openOrCreateDatabase(
                "MyDb", Context.MODE_PRIVATE, null);
        //2. 初始資料庫中的資料表結構
        String sql = "create table if not exists person(" +
                "height double," +
                "weight double," +
                "mesDate varchar(10) primary key," +
                "gender boolean," +
                "bmi double" +
                ");";
        mydb.execSQL(sql);
        return mydb;
    }
}
