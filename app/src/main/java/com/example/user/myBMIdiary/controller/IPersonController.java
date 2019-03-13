package com.example.user.myBMIdiary.controller;

import android.content.Context;

import com.example.user.myBMIdiary.model.Person;

import java.util.ArrayList;

public interface IPersonController {
    //新增
    public long insert(Context context, Person data);

    //修改
    public int update(Context context, Person data);

    //刪除
    public int delete(Context context, String mesDate);

    //查詢1, 依測日期
    public Person findByMesDate(Context context, String mesDate);

    //查詢2, 查全部
    public ArrayList<Person> findAll(Context context);
}
