package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void aboutBtnClicked(View view){
        Button aboutBtn = findViewById(R.id.about_btn);
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }
}