package com.karim.lebdrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String french = "french";
    private static final String english = "english";
    private static final String arabic = "arabic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readQuestionsFile(english);
        readQuestionsFile(french);
        readQuestionsFile(arabic);
    }

    public void aboutBtnClicked(View view) {
        Button aboutBtn = findViewById(R.id.about_btn);
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void calendarBtnClicked(View view) {
        FloatingActionButton floatBtn = findViewById(R.id.float_button);
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
        Toast.makeText(this, "Pick your exam date", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String setTime = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());

        Toast.makeText(this, "Exam date set to: " + setTime, Toast.LENGTH_LONG).show();
    }

    private void readQuestionsFile(String language) {
        // method that reads from a file and inserts the data in our database
        String signsTable = language + "_signs";
        String questionsTable = language + "_questions";

        String f = language + "_questions.txt";
        String f2 = language + "_questions_correct.txt";
        String f3 = language + "_questions_wrong_answers1.txt";
        String f4 = language + "_questions_wrong_answers2.txt";

        String f5 = language + "_signs_correct.txt";
        String f6 = language + "_signs_wrong1.txt";
        String f7 = language + "_signs_wrong2.txt";

        boolean questionsInserted = false;

        // inserting questions
        readFile(questionsTable, f, "QUESTION", questionsInserted);
        questionsInserted = true;
        readFile(questionsTable, f2, "CORRECT_ANSWER", questionsInserted);
        readFile(questionsTable, f3, "WRONG_ANSWER1", questionsInserted);
        readFile(questionsTable, f4, "WRONG_ANSWER2", questionsInserted);
        questionsInserted = false;

        // inserting signs
        readFile(signsTable, f5, "CORRECT_ANSWER", questionsInserted);
        questionsInserted = true;
        readFile(signsTable, f6, "WRONG_ANSWER1", questionsInserted);
        readFile(signsTable, f7, "WRONG_ANSWER2", questionsInserted);
    }

    private void readFile(String tableName, String fileName, String attribute, boolean inserted) {
        BufferedReader reader = null;
        try {
            SQLiteDatabase db;
            SQLiteOpenHelper questionSQLiteOpenHelper = new QuestionSQLiteOpenHelper(this);
            db = questionSQLiteOpenHelper.getWritableDatabase();
            String data = "";

            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(fileName), "UTF-8"));
            long id = 1;
            // do reading, loop until end of file reading
            while ((data = reader.readLine()) != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(attribute, data);
                if (!inserted) // Inserting the questions as a first step
                    db.insert(tableName, null, contentValues);
                else // updating the database to include all the attributes
                    db.update(tableName, contentValues, "_id = ?", new String[]{Long.toString(id)});
                id++;
                System.out.println("data: " + contentValues.toString());
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error Found!");
        }
    }

}