package com.example.user.myBMIdiary.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myBMIdiary.model.MyDB;
import com.example.user.myBMIdiary.model.Person;

import java.util.ArrayList;

public class PersonControllerImpl implements IPersonController {

    @Override
    public long insert(Context context, Person data) {
        SQLiteDatabase db = MyDB.getMyDb(context);
        try {
            //1. 指定重組insert語法的欄位清單 及 值清單
            ContentValues contentValues = new ContentValues();
            contentValues.put("height", data.getHeight());
            contentValues.put("weight", data.getWeight());
            contentValues.put("mesDate", data.getMesDate());
            contentValues.put("gender", data.isGender());
            contentValues.put("bmi", data.getBmi());

            //2. 執行insert動作
            long rows = db.insert(Person.tableName, null, contentValues);

            return rows;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return -1;
    }

    @Override
    public int update(Context context, Person data) {
        SQLiteDatabase db = MyDB.getMyDb(context);
        try {
            //1. 指定重組update語法的欄位清單 及 值清單
            ContentValues contentValues = new ContentValues();
            contentValues.put("height", data.getHeight());
            contentValues.put("weight", data.getWeight());
            contentValues.put("gender", data.isGender());
            contentValues.put("bmi", data.getBmi());
//          update person set height=175, weight=69, gender=1, bmi=23 where mesDate='2018-11-17'
            //2. 執行update動作
            int rows = db.update(
                    Person.tableName,
                    contentValues,
                    "mesDate=?",
                    new String[]{data.getMesDate()}
            );
            return rows;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return -1;
    }

    @Override
    public int delete(Context context, String mesDate) {
        SQLiteDatabase db = MyDB.getMyDb(context);
        try {
            //delete person where mesDate='2018-11-16'
            int rows = db.delete(Person.tableName, "mesDate=?", new String[]{mesDate});
            return rows;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return -1;
    }

    @Override
    public Person findByMesDate(Context context, String mesDate) {
        SQLiteDatabase db = MyDB.getMyDb(context);
        try {
            //"Select 欄位清單 from 資料表名稱 where 條件欄位名稱=值";
            String sql = String.format("Select * from %s where mesDate=?",
                    Person.tableName);
            Cursor cursor = db.rawQuery(sql, new String[]{mesDate});
            Person data = new Person();
            if (cursor.moveToFirst()) {
                //查詢有記錄
                data.setMesDate(cursor.getString(cursor.getColumnIndex("mesDate")));
                data.setGender(cursor.getInt(3) == 1 ? true : false);
                data.setHeight(cursor.getDouble(0));
                data.setWeight(cursor.getDouble(1));
                return data;
            } else {
                //查詢沒有記錄
                System.out.println("not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Person> findAll(Context context) {
        SQLiteDatabase db = MyDB.getMyDb(context);
        try {
            //"Select 欄位清單 from 資料表名稱 where 條件欄位名稱=值";
            String sql = String.format("Select * from %s", Person.tableName);
            Cursor cursor = db.rawQuery(sql, null);
            ArrayList<Person> datas = new ArrayList<>();
            while (cursor.moveToNext()) {
                Person data = new Person();
                //查詢有記錄
                data.setMesDate(cursor.getString(cursor.getColumnIndex("mesDate")));
                data.setGender(cursor.getInt(3) == 1 ? true : false);
                data.setHeight(cursor.getDouble(0));
                data.setWeight(cursor.getDouble(1));
                datas.add(data);
            }
            return datas;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
        return null;
    }
}
