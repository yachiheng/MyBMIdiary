package com.example.user.myBMIdiary.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.user.myBMIdiary.R;


public class MySharedPreference {
    /**
     * 取得SharedPreference資源存取檔案
     * @param context
     * @return SharedPreference資源存取物件
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    /**
     * 寫入主題
     * @param context
     * @param themeId 傳遞主題的資源代號
     */
    public static void setTheme(Context context, int themeId) {
        SharedPreferences pref = getSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("theme", themeId);
        editor.commit();
    }

    /**
     * 取得主題資源代號
     * @param context
     * @return 回傳資源代號
     */
    public static int getTheme(Context context) {
        SharedPreferences pref = getSharedPreferences(context);
        final int themeId = pref.getInt("theme", R.style.AppThemeDark);
        return themeId;
    }
}
