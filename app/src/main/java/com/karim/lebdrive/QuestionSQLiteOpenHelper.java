package com.karim.lebdrive;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestionSQLiteOpenHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "Driving Questions";
    private static final int DB_VERSION = 1;

    public QuestionSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE TEST_DATE(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEST_DAY TEXT);");

        db.execSQL("CREATE TABLE english_signs(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SIGN_RESOURCE_ID INTEGER," +
                "CORRECT_ANSWER TEXT," +
                "WRONG_ANSWER1 TEXT," +
                "WRONG_ANSWER2 TEXT);");

        db.execSQL("CREATE TABLE english_questions(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "QUESTION TEXT," +
                "CORRECT_ANSWER TEXT," +
                "WRONG_ANSWER1 TEXT," +
                "WRONG_ANSWER2 TEXT);");

        db.execSQL("CREATE TABLE french_signs(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SIGN_RESOURCE_ID INTEGER," +
                "CORRECT_ANSWER TEXT," +
                "WRONG_ANSWER1 TEXT," +
                "WRONG_ANSWER2 TEXT);");

        db.execSQL("CREATE TABLE french_questions(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "QUESTION TEXT," +
                "CORRECT_ANSWER TEXT," +
                "WRONG_ANSWER1 TEXT," +
                "WRONG_ANSWER2 TEXT);");

        db.execSQL("CREATE TABLE arabic_signs(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SIGN_RESOURCE_ID INTEGER," +
                "CORRECT_ANSWER TEXT," +
                "WRONG_ANSWER1 TEXT," +
                "WRONG_ANSWER2 TEXT);");

        db.execSQL("CREATE TABLE arabic_questions(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "QUESTION TEXT," +
                "CORRECT_ANSWER TEXT," +
                "WRONG_ANSWER1 TEXT," +
                "WRONG_ANSWER2 TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
