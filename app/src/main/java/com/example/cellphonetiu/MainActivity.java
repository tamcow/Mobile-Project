package com.example.cellphonetiu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // godwolf@gmail.com
        // 333
//  Home to Login
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        }, 3000);

    }
}