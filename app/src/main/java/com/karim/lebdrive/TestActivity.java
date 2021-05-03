package com.karim.lebdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;
    private long id;
    String language = "";
    String testType = "";
    View view;
    Button btn1;
    Button btn2;
    Button btn3;
    int score = 0;
    int qstNbr = 1;
    int ticks = 0;
    boolean running = true;
    String[] answers = new String[3];
    Button[] buttons = new Button[3];
    ArrayList<Integer> questionArr = new ArrayList<Integer>();
    ArrayList<Integer> signsArr = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        language = intent.getStringExtra("Language");

        System.out.println("testtype: " + language);
        testType = intent.getStringExtra("Test Type");
        TextView scoreTxt = findViewById(R.id.score_text_view);
        TextView qstDisplay = findViewById(R.id.question_nbr_text_view);

        scoreTxt.setText("Score: 0/30");
        qstDisplay.setText("1/30");

        btn1 = findViewById(R.id.answer_button_1);
        btn2 = findViewById(R.id.answer_button_2);
        btn3 = findViewById(R.id.answer_button_3);
        buttons = new Button[]{btn1, btn2, btn3};

        testTypePicker();
        timer();
    }

    public void testTypePicker() {
        if (testType.equalsIgnoreCase("questions"))
            startQuestionQuiz(language);
        else if (testType.equalsIgnoreCase("signs"))
            startSignQuiz(language);
        else {
            for (int i = 0; i < 20; i++)
                startQuestionQuiz(language);
            for (int i = 0; i < 10; i++)
                startSignQuiz(language);
        }
    }

    public void timer() {
        TextView timerText = findViewById(R.id.elapsed_time_text_view);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (qstNbr > 30) {
                    running = false;
                    Intent intent = new Intent(TestActivity.this, ScoreActivity.class);
                    intent.putExtra("Score", score);
                    intent.putExtra("Test Language", language);
                    startActivity(intent);
                    return;
                }

                if (running) {
                    ticks++;
                }

                int seconds = ticks % 60;
                int minutes = ticks / 60 % 60;

                String timeElapsed = minutes + "\' " + seconds + "\"";
                timerText.setText(timeElapsed);
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void startQuestionQuiz(String language) {
        int rand = 0;
        int correctAnswer = 0;
        TextView questionTxt = findViewById(R.id.question_text_view);
        questionTxt.setVisibility(View.VISIBLE);

        btn1.setBackgroundColor(getResources().getColor(R.color.blue_3));
        btn2.setBackgroundColor(getResources().getColor(R.color.blue_3));
        btn3.setBackgroundColor(getResources().getColor(R.color.blue_3));

        try {
            SQLiteOpenHelper sqLiteOpenHelper = new QuestionSQLiteOpenHelper(this);
            db = sqLiteOpenHelper.getReadableDatabase();
            id = chooseQuestionId();

            cursor = db.query(language + "_questions",
                    new String[]{"QUESTION", "CORRECT_ANSWER", "WRONG_ANSWER1", "WRONG_ANSWER2"},
                    "_id = ?", new String[]{Long.toString(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                String question = cursor.getString(0);
                String correct_answer = cursor.getString(1);
                String wrong_answer1 = cursor.getString(2);
                String wrong_answer2 = cursor.getString(3);

                questionTxt.setText(question);
                answers = new String[]{correct_answer, wrong_answer1, wrong_answer2};
                correctAnswer = dispenseAnswers(buttons, answers);
            }
            checkAnswer(correctAnswer);
        } catch (Exception e) {
            Toast.makeText(this, "Database not available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void startSignQuiz(String language) {
        int rand = 0;
        int correctAnswer = 0;
        ImageView imageView = findViewById(R.id.sign_image);
        imageView.setVisibility(View.VISIBLE);

        btn1.setBackgroundColor(getResources().getColor(R.color.blue_3));
        btn2.setBackgroundColor(getResources().getColor(R.color.blue_3));
        btn3.setBackgroundColor(getResources().getColor(R.color.blue_3));

        try {
            SQLiteOpenHelper sqLiteOpenHelper = new QuestionSQLiteOpenHelper(this);
            db = sqLiteOpenHelper.getReadableDatabase();
            id = chooseSignId();

            cursor = db.query(language + "_signs",
                    new String[]{"SIGN_RESOURCE_ID", "CORRECT_ANSWER", "WRONG_ANSWER1", "WRONG_ANSWER2"},
                    "_id = ?", new String[]{Long.toString(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                int imgSrc = cursor.getInt(0);
                String correct_answer = cursor.getString(1);
                String wrong_answer1 = cursor.getString(2);
                String wrong_answer2 = cursor.getString(3);

                imageView.setImageResource(imgSrc);
                answers = new String[]{correct_answer, wrong_answer1, wrong_answer2};
                correctAnswer = dispenseAnswers(buttons, answers);
            }
            checkAnswer(correctAnswer);
        } catch (Exception e) {
            Toast.makeText(this, "Database not available.", Toast.LENGTH_SHORT).show();
        }
    }

    public int chooseQuestionId() {
        int pickId = (int) (Math.random() * 239) + 1;
        while (questionArr.contains(pickId))
            pickId = (int) (Math.random() * 239) + 1;
        questionArr.add(pickId);

        return pickId;
    }

    public int chooseSignId() {
        int pickId = (int) (Math.random() * 151) + 1;
        while (signsArr.contains(pickId))
            pickId = (int) (Math.random() * 151) + 1;
        signsArr.add(pickId);

        return pickId;
    }

    public int dispenseAnswers(Button[] btn, String[] txt) {
        ArrayList<Integer> randNbrs = new ArrayList<>();
        int random = (int) (Math.random() * 3) + 1;
        btn[random - 1].setText(txt[0]);
        randNbrs.add(random);
        int correctPlace = random;

        for (int i = 1; i < 3; i++) {
            random = (int) (Math.random() * 3) + 1;
            while (randNbrs.contains(random))
                random = (int) (Math.random() * 3) + 1;
            randNbrs.add(random);
            btn[random - 1].setText(txt[i]);
        }
        return correctPlace;
    }

    public void checkAnswer(int answer) {
        TextView scoreTxt = findViewById(R.id.score_text_view);
        TextView qstDisplay = findViewById(R.id.question_nbr_text_view);
        Handler handler = new Handler();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn1 == buttons[answer - 1]) {
                    btn1.setBackgroundColor(getResources().getColor(R.color.green));
                    score++;
                    scoreTxt.setText("Score: " + score + "/30");
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                } else if (btn1 != buttons[answer - 1] && btn2 == buttons[answer - 1]) {
                    btn1.setBackgroundColor(getResources().getColor(R.color.red));
                    btn2.setBackgroundColor(getResources().getColor(R.color.green));
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                } else if (btn1 != buttons[answer - 1] && btn3 == buttons[answer - 1]) {
                    btn1.setBackgroundColor(getResources().getColor(R.color.red));
                    btn3.setBackgroundColor(getResources().getColor(R.color.green));
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testTypePicker();
                    }
                }, 1000);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn2 == buttons[answer - 1]) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.green));
                    score++;
                    scoreTxt.setText("Score: " + score + "/30");
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                } else if (btn2 != buttons[answer - 1] && btn1 == buttons[answer - 1]) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.red));
                    btn1.setBackgroundColor(getResources().getColor(R.color.green));
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                } else if (btn2 != buttons[answer - 1] && btn3 == buttons[answer - 1]) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.red));
                    btn3.setBackgroundColor(getResources().getColor(R.color.green));
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testTypePicker();
                    }
                }, 1000);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn3 == buttons[answer - 1]) {
                    btn3.setBackgroundColor(getResources().getColor(R.color.green));
                    score++;
                    scoreTxt.setText("Score: " + score + "/30");
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                } else if (btn3 != buttons[answer - 1] && btn1 == buttons[answer - 1]) {
                    btn3.setBackgroundColor(getResources().getColor(R.color.red));
                    btn1.setBackgroundColor(getResources().getColor(R.color.green));
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                } else if (btn3 != buttons[answer - 1] && btn2 == buttons[answer - 1]) {
                    btn3.setBackgroundColor(getResources().getColor(R.color.red));
                    btn2.setBackgroundColor(getResources().getColor(R.color.green));
                    qstNbr++;
                    qstDisplay.setText(qstNbr + "/30");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testTypePicker();
                    }
                }, 1000);
            }
        });
    }

}