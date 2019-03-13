package com.example.user.myBMIdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        //取得前一個Activity所傳遞的 Intent
        Intent prevIntent = getIntent();
        double height = prevIntent.getDoubleExtra("height", 0);
        double weight = prevIntent.getDoubleExtra("weight", 0);
        String mesDate = prevIntent.getStringExtra("mesDate");
        boolean gender = prevIntent.getBooleanExtra("gender", false);

        TextView tv5 = findViewById(R.id.textView5);

        double bmi = weight / Math.pow(height / 100, 2);
        tv5.setText("BMI:" + bmi);

        //計算出bmi等級 http://health99.hpa.gov.tw/OnlinkHealth/Onlink_BMI.aspx
        int bmiLevel = 0;
        if(bmi<18.5){
            bmiLevel = 1;
        }else if(bmi <24){
            bmiLevel = 2;
        }else if(bmi <27){
            bmiLevel = 3;
        }else if(bmi <30){
            bmiLevel = 4;
        }else if(bmi <35){
            bmiLevel =5;
        }else{
            bmiLevel =6;
        }
        //指定回送的 Intent
        prevIntent.putExtra("bmi", bmi);

        setResult(bmiLevel, prevIntent);
        finish();
    }
}
