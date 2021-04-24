package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TestCatalogueActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<TestType> typeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_catalogue);

        recyclerView = findViewById(R.id.recyclerView);
        initData();
        setRecyclerView();
        arrowClicked();
    }

    private void setRecyclerView() {
        TestTypeAdapter testTypeAdapter = new TestTypeAdapter(typeList);
        recyclerView.setAdapter(testTypeAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {
        typeList = new ArrayList<>();
        typeList.add(new TestType("Full test", getString(R.string.full_test_txt), "Start Test"));
        typeList.add(new TestType("Signs-only test", getString(R.string.signs_test_txt), "Start Test"));
        typeList.add(new TestType("Questions-only test", getString(R.string.questions_test_txt), "Start Test"));
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
                Intent intent = new Intent(TestCatalogueActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestCatalogueActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}