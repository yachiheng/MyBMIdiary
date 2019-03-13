package com.example.user.myBMIdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent prevIntent = getIntent();

        TextView tvHeight = findViewById(R.id.textViewHeight);
        TextView tvWeight = findViewById(R.id.textViewWeight);
        TextView tvMesDate = findViewById(R.id.textViewMesDate);
        TextView tvBMI = findViewById(R.id.textViewBMI);
        ImageView imageView = findViewById(R.id.imageView);

        tvHeight.setText(prevIntent.getDoubleExtra("height", 0) + "");
        tvWeight.setText(prevIntent.getDoubleExtra("weight", 0) + "");
        tvMesDate.setText(prevIntent.getStringExtra("mesDate"));
        tvBMI.setText(prevIntent.getDoubleExtra("bmi", 0) + "");

        imageView.setImageResource(prevIntent.getIntExtra("img", R.drawable.newbmif1));

    }
}
