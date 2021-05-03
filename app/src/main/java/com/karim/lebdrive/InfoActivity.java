package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        checkCenters();
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
}