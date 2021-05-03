package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {
    String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        int score = intent.getIntExtra("Score", 0);
        language = intent.getStringExtra("Test Language");

        TextView textView = findViewById(R.id.final_score_text_view);
        textView.setText(score + "/30");

        suggestionText(score);
        finishText();
        arrowClicked();
    }

    public void finishText() {
        TextView textView = findViewById(R.id.finish_statement_text_view);

        if (language.equalsIgnoreCase("english"))
            textView.setText(R.string.finish_txt_eng);
        else if (language.equalsIgnoreCase("french"))
            textView.setText(R.string.finish_txt_fr);
        else
            textView.setText(R.string.finish_txt_ar);
    }

    public void suggestionText(int score) {
        TextView textView = findViewById(R.id.eligible_text_view);

        if (score < 25)
            if (language.equalsIgnoreCase("english"))
                textView.setText(R.string.fail_text_eng);
            else if (language.equalsIgnoreCase("french"))
                textView.setText(R.string.fail_text_fr);
            else
                textView.setText(R.string.fail_text_ar);

        else if (score == 25)
            if (language.equalsIgnoreCase("english"))
                textView.setText(R.string.need_more_practice_txt_eng);
            else if (language.equalsIgnoreCase("french"))
                textView.setText(R.string.need_more_practice_txt_fr);
            else
                textView.setText(R.string.need_more_practice_txt_ar);

        else {
            if (language.equalsIgnoreCase("english"))
                textView.setText(R.string.success_txt_eng);
            else if (language.equalsIgnoreCase("french"))
                textView.setText(R.string.success_txt_fr);
            else
                textView.setText(R.string.success_txt_ar);
        }
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
                Intent intent = new Intent(ScoreActivity.this, TestCatalogueActivity.class);
                startActivity(intent);
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, TestCatalogueActivity.class);
                startActivity(intent);
            }
        });
    }
}