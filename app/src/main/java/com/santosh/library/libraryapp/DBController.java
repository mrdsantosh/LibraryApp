package com.santosh.library.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by sd185135 on 6/30/2017.
 */

public class DBController extends SQLiteOpenHelper {

    public DBController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "StudentRecord.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCHOOLS(ID INTEGER PRIMARY KEY AUTOINCREMENT, SCHOOLNAME TEXT UNIQUE NOT NULL);");
        db.execSQL("CREATE TABLE SCHOOL_STUDENTS(ID INTEGER PRIMARY KEY AUTOINCREMENT, SCHOOLID INTEGER NOT NULL, STUDENTNAME TEXT NOT NULL, FOREIGN KEY (SCHOOLID) REFERENCES SCHOOLS(ID) ON DELETE CASCADE);");
        db.execSQL("CREATE TABLE STUDENT_BOOKS(ID INTEGER PRIMARY KEY AUTOINCREMENT, STUDENTID INTEGER NOT NULL, BOOKNAME TEXT UNIQUE NOT NULL, BOOKLEVEL TEXT NOT NULL, ISSUEDATE TEXT NOT NULL, FOREIGN KEY (STUDENTID) REFERENCES SCHOOL_STUDENTS(ID) ON DELETE CASCADE);");
        Log.e("DATABASE", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENT_BOOKS;");
        db.execSQL("DROP TABLE IF EXISTS SCHOOL_STUDENTS;");
        db.execSQL("DROP TABLE IF EXISTS SCHOOLS;");
        Log.e("DATABASE", "Database dropped");
        onCreate(db);
    }

    public void insertStudent(String studentName, String schoolName, String bookName, String bookLevel, String issueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("SCHOOLNAME", studentName);
            long schoolId = db.insertOrThrow("SCHOOLS", null, contentValues);

            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("SCHOOLID", schoolId);
            contentValues1.put("STUDENTNAME", studentName);
            long studentId = db.insertOrThrow("SCHOOL_STUDENTS", null, contentValues1);

            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("STUDENTID", studentId);
            contentValues2.put("BOOKNAME", bookName);
            contentValues2.put("BOOKLEVEL", bookLevel);
            contentValues2.put("ISSUEDATE", issueDate);
            db.insertOrThrow("STUDENT_BOOKS", null, contentValues2);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteStudent(String studentName) {
        this.getWritableDatabase().delete("SCHOOL_STUDENTS", "STUDENTNAME='" + studentName + "'", null);
    }

    public void listStudents(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM STUDENT_BOOKS", null);
        textView.setText("");
        while (cursor.moveToNext()) {
            textView.append(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + "\n");
        }
    }

    public boolean findStudent(String studentName) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM SCHOOL_STUDENTS WHERE STUDENTNAME='" + studentName + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}