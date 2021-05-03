package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        checkVideos();
        checkCenters();
        arrowClicked();
    }

    public void checkCenters() {
        Button checkCentersBtn = findViewById(R.id.check_centers_button);

        checkCentersBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout = findViewById(R.id.map);

                if (frameLayout.getVisibility() == View.GONE) {
                    frameLayout.setVisibility(View.VISIBLE);

                    Fragment fragment = new MapsFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.map, fragment)
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else
                    frameLayout.setVisibility(View.GONE);
            }
        });
    }

    public void checkVideos() {
        Button checkVideoBtn = findViewById(R.id.check_videos_button);

        checkVideoBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                ScrollView scrollView = findViewById(R.id.videos_scroll_view);

                if (scrollView.getVisibility() == View.GONE) {
                    scrollView.setVisibility(View.VISIBLE);

                    Fragment fragment = new YoutubeVideoFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.youtube_vid, fragment)
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else
                    scrollView.setVisibility(View.GONE);
            }
        });
    }

    public void arrowClicked() {
        ImageView img = findViewById(R.id.back_logo);
        TextView txt = findViewById(R.id.back_text_view);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        img.startAnimation(animation);
        txt.startAnimation(animation);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}