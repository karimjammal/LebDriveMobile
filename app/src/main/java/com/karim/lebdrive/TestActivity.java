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
    Button btn1;
    Button btn2;
    Button btn3;
    int score = 0;
    int qstToDisplay = 1;
    int qstNbr = 0;
    int signsNbr = 0;
    int ticks = 0;
    boolean btnClicked = false;
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
        // Getting the languages picked by the user
        language = intent.getStringExtra("Language");
        System.out.println("testtype: " + language);

        // Getting the type of the test (Full, Signs-only, Questions-only)
        testType = intent.getStringExtra("Test Type");
        System.out.println("testtype: " + testType);

        // Initializing score to 0 and questions to 1
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

    // Methods that check which test type is picked and displays accrodingly
    public void testTypePicker() {
        if (testType.equalsIgnoreCase("questions")) {
            startQuestionQuiz(language);
        } else if (testType.equalsIgnoreCase("signs")) {
            startSignQuiz(language);
        } else {
            if (qstNbr >= 20 && signsNbr < 10) {
                TextView questionTxt = findViewById(R.id.question_text_view);
                questionTxt.setVisibility(View.GONE);
                startSignQuiz(language);
                signsNbr++;
            } else
                startQuestionQuiz(language);
            qstNbr++;
        }
    }

    // Method to check the test's ellapsed time
    public void timer() {
        TextView timerText = findViewById(R.id.elapsed_time_text_view);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (qstToDisplay >= 30) {
                    if (btnClicked) {
                        running = false;
                        Handler h2 = new Handler();
                        h2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(TestActivity.this, ScoreActivity.class);
                                intent.putExtra("Score", score);
                                intent.putExtra("Test Language", language);
                                startActivity(intent);
                            }
                        }, 1000);
                        return;
                    }
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

    // Method that displays the "Questions" questions of the test
    public void startQuestionQuiz(String language) {
        int rand = 0;
        int correctAnswer = 0;
        TextView questionTxt = findViewById(R.id.question_text_view);
        questionTxt.setVisibility(View.VISIBLE);

        // Re-initializing colors of the buttons
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

    // Method that displays the "Signs" questions of the test
    public void startSignQuiz(String language) {
        int rand = 0;
        int correctAnswer = 0;
        ImageView imageView = findViewById(R.id.sign_image);
        imageView.setVisibility(View.VISIBLE);

        // Re-initializing colors of the buttons
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

    // Method that picks the "Question" question,
    // and checks if it was already displayed or not
    public int chooseQuestionId() {
        int pickId = (int) (Math.random() * 239) + 1;
        while (questionArr.contains(pickId))
            pickId = (int) (Math.random() * 239) + 1;
        questionArr.add(pickId);

        return pickId;
    }

    // Method that picks the "Sign" question,
    // and checks if it was already displayed or not
    public int chooseSignId() {
        int pickId = (int) (Math.random() * 151) + 1;
        while (signsArr.contains(pickId))
            pickId = (int) (Math.random() * 151) + 1;
        signsArr.add(pickId);

        return pickId;
    }

    // Method to assign the correct answer to any
    // of the 3 buttons and returns the correct (or button) place of the answer
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

    // Method to check the correct answer. Each button has its own click listener
    // Each will behave according to being pressed, which explains the use of 3 click listeners
    // And a repetitive but custom method
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
                } else if (btn1 != buttons[answer - 1] && btn2 == buttons[answer - 1]) {
                    btn1.setBackgroundColor(getResources().getColor(R.color.red));
                    btn2.setBackgroundColor(getResources().getColor(R.color.green));
                } else if (btn1 != buttons[answer - 1] && btn3 == buttons[answer - 1]) {
                    btn1.setBackgroundColor(getResources().getColor(R.color.red));
                    btn3.setBackgroundColor(getResources().getColor(R.color.green));
                }
                qstToDisplay++;
                if (qstToDisplay == 31)
                    btnClicked = true;
                    // Delay in displaying the next question, in order to
                    // let the users check for their correct/wrong answers
                else
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            qstDisplay.setText(qstToDisplay + "/30");
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
                } else if (btn2 != buttons[answer - 1] && btn1 == buttons[answer - 1]) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.red));
                    btn1.setBackgroundColor(getResources().getColor(R.color.green));
                } else if (btn2 != buttons[answer - 1] && btn3 == buttons[answer - 1]) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.red));
                    btn3.setBackgroundColor(getResources().getColor(R.color.green));
                }
                qstToDisplay++;
                if (qstToDisplay == 31)
                    btnClicked = true;
                else
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            qstDisplay.setText(qstToDisplay + "/30");
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
                } else if (btn3 != buttons[answer - 1] && btn1 == buttons[answer - 1]) {
                    btn3.setBackgroundColor(getResources().getColor(R.color.red));
                    btn1.setBackgroundColor(getResources().getColor(R.color.green));
                } else if (btn3 != buttons[answer - 1] && btn2 == buttons[answer - 1]) {
                    btn3.setBackgroundColor(getResources().getColor(R.color.red));
                    btn2.setBackgroundColor(getResources().getColor(R.color.green));
                }
                qstToDisplay++;
                if (qstToDisplay == 31)
                    btnClicked = true;
                else
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            qstDisplay.setText(qstToDisplay + "/30");
                            testTypePicker();
                        }
                    }, 1000);
            }
        });
    }

}