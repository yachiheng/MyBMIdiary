package com.example.user.myBMIdiary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.user.myBMIdiary.controller.PersonControllerImpl;
import com.example.user.myBMIdiary.model.MySharedPreference;
import com.example.user.myBMIdiary.model.Person;

import java.util.Calendar;

public class EntryActivity extends AppCompatActivity {

//    private static final int ITEM1 = 399;
//    private static final int ITEM2 = 499;

    EditText editHeight, edithWeight, editMeasure;
    Switch switchGender;
    //存放修改的 測量日期
    String mesDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SharedPreferences pref = getSharedPreferences();
//        final int themeId = pref.getInt("theme", R.style.AppThemeDark);
        final int themeId = MySharedPreference.getTheme(this);
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editHeight = findViewById(R.id.editText);
        edithWeight = findViewById(R.id.editText2);
        editMeasure = findViewById(R.id.editText3);
        switchGender = findViewById(R.id.switch1);
        switchGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("---1 " + isChecked);
                //buttonView.setText(isChecked ? "男" : "女");
                buttonView.setText(isChecked ? R.string.entryactivity_male_title :
                        R.string.entryactivity_female_title);
            }
        });
        editMeasure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //取得系統當前的日期
                    Calendar now = Calendar.getInstance();

                    //顯示日期對話框
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            EntryActivity.this,
                            themeId,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int month, int dayOfMonth) {
                                    editMeasure.setText(String.format("%s-%s-%s",
                                            year, month + 1, dayOfMonth));
                                }
                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)

                    );
                    //顯示日期對話框
                    datePickerDialog.show();
                }
            }
        });

        //取得前一個Activity所傳遞的資料
        Intent preIntent = getIntent();
        mesDate = preIntent.getStringExtra("mesDate");
        if (mesDate != null) {
            //修改狀態
            PersonControllerImpl personController = new PersonControllerImpl();
            Person data = personController.findByMesDate(this, mesDate);
            if(data!=null) {
                editHeight.setText(data.getHeight() + "");
                edithWeight.setText(data.getWeight() + "");
                editMeasure.setText(mesDate);
                switchGender.setChecked(data.isGender());
                //取消與使用者的回應功能
                editMeasure.setEnabled(false);
            }else{
                finish();
            }
        }
    }

    /**
     * 在該Activity建立選項式功能表
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(0,ITEM1,1,"Dark2");
        //menu.add(0,ITEM2,2,"Light2");
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 承擔選項式功能的動作
     *
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        SharedPreferences pref = getSharedPreferences();
//        SharedPreferences.Editor editor = pref.edit();
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.menu_dark:
//                editor.putInt("theme", R.style.AppThemeDark);
                MySharedPreference.setTheme(this, R.style.AppThemeDark);
                break;
            case R.id.menu_light:
//                editor.putInt("theme", R.style.AppTheme);
                MySharedPreference.setTheme(this, R.style.AppTheme);
                break;
//            case ITEM1:
//                break;
//            case ITEM2:
//                break;
        }
//        editor.commit();
        //重新啟動APP
        Intent intent = new Intent(this, EntryActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //指定成新的工作執行緒
        startActivity(intent);

        this.finish(); //結束當前的Activity
        //System.exit(0);
        //Runtime.getRuntime().exit(0); //結束APP
        return super.onOptionsItemSelected(item);
    }

    public void switchClick(View view) {
        System.out.println("---2 " + switchGender.isChecked());
    }

    public void buttonClick(View view) {
        double height = Double.parseDouble(editHeight.getText().toString());
        double weight = Double.parseDouble(edithWeight.getText().toString());
        String mesDate = editMeasure.getText().toString();
        boolean gender = switchGender.isChecked();
        switch (view.getId()) {
            case R.id.buttonDetail:
                Intent intent = new Intent(this, CalcActivity.class);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("mesDate", mesDate);
                intent.putExtra("gender", gender);
                //startActivity(intent);
                startActivityForResult(intent, 100);
                //finish();
                break;
            case R.id.buttonSave:
                //準備資料媒介物件
                Person data = new Person(height, weight, mesDate, gender);
                //建立資料控制物件
                PersonControllerImpl personController = new PersonControllerImpl();
                //呼叫插入或者更新方法
                long rows = 0;
                //判斷是否為插入
                if (EntryActivity.this.mesDate == null) {
                    rows = personController.insert(this, data);
                } else {
                    rows = personController.update(this, data);
                }
                if (rows >= 0) {
                    //System.out.println("success");
                    //簡單式Toast
                    Toast.makeText(this,"存檔成功!",Toast.LENGTH_SHORT).show();
                    //複雜式Toast
//                  Toast toast =  Toast.makeText(this, "存檔成功", Toast.LENGTH_SHORT);
//                  toast.setGravity(Gravity.CENTER,0,0); //
//                  toast.show();
                    finish();
                } else {
                    //System.out.println("fail");
                    Toast.makeText(this,"存檔失敗!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                //判斷性別
                boolean gender = data.getBooleanExtra("gender", false);
                if (gender) { //男生
                    switch (resultCode) {
                        case 1:
                            data.putExtra("img", R.drawable.newbmim1);
                            break;
                        case 2:
                            data.putExtra("img", R.drawable.newbmim2);
                            break;
                        case 3:
                            data.putExtra("img", R.drawable.newbmim3);
                            break;
                        case 4:
                            data.putExtra("img", R.drawable.newbmim4);
                            break;
                        case 5:
                            data.putExtra("img", R.drawable.newbmim5);
                            break;
                        case 6:
                            data.putExtra("img", R.drawable.newbmim6);
                            break;
                    }
                } else {//女生
                    switch (resultCode) {
                        case 1:
                            data.putExtra("img", R.drawable.newbmif1);
                            break;
                        case 2:
                            data.putExtra("img", R.drawable.newbmif2);
                            break;
                        case 3:
                            data.putExtra("img", R.drawable.newbmif3);
                            break;
                        case 4:
                            data.putExtra("img", R.drawable.newbmif4);
                            break;
                        case 5:
                            data.putExtra("img", R.drawable.newbmif5);
                            break;
                        case 6:
                            data.putExtra("img", R.drawable.newbmif6);
                            break;
                    }
                }
                data.setClass(this, DetailActivity.class);
                startActivity(data);
                break;
        }
    }
}
