package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        arrowClicked();
    }

    // Method to trigger the back button
    public void arrowClicked() {
        ImageView img = findViewById(R.id.back_logo);
        TextView txt = findViewById(R.id.back_text_view);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        img.startAnimation(animation);
        txt.startAnimation(animation);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}