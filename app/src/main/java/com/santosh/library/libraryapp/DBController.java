package com.santosh.library.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

public class DBController extends SQLiteOpenHelper {

    private static DBController sInstance;

    private static final String DATABASE_NAME = "StudentsDB";
    private static final String DATABASE_TABLE = "STUDENT_BOOKS";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public static synchronized DBController getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBController(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, SCHOOL_NAME TEXT NOT NULL, STUDENT_NAME TEXT NOT NULL, BOOK_NAME TEXT NOT NULL, BOOK_LEVEL TEXT NOT NULL, ISSUE_DATE DATE NOT NULL);");
        Log.e("DatabaseHelper", DATABASE_TABLE + " created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + ";");
        onCreate(db);
    }

    public void insertStudent(String studentName, String schoolName, String bookName, String bookLevel, String issueDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SCHOOL_NAME", studentName);
        contentValues.put("STUDENT_NAME", studentName);
        contentValues.put("BOOK_NAME", bookName);
        contentValues.put("BOOK_LEVEL", bookLevel);
        contentValues.put("ISSUE_DATE", issueDate);
        this.getWritableDatabase().insertOrThrow(DATABASE_TABLE, null, contentValues);
    }

    public void deleteStudent(String schoolName, String studentName) {
        this.getWritableDatabase().delete(DATABASE_TABLE, "SCHOOL_NAME='" + schoolName + "' AND STUDENT_NAME='" + studentName + "'", null);
    }

    public void listStudents(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        textView.setText("");
        while (cursor.moveToNext()) {
            textView.append(cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5) + "\n");
        }
    }

    public boolean findStudent(String studentName) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE SCHOOL_NAME='" + studentName + "' AND STUDENT_NAME='" + studentName + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}