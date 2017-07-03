package com.santosh.library.libraryapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class StudentActivity extends AppCompatActivity {

    EditText schoolName, studentName, bookName, bookLevel, issueDate;
    DatePickerDialog datePickerDialog;
    DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main);

        schoolName = (EditText) findViewById(R.id.schoolname_input);
        studentName = (EditText) findViewById(R.id.studentname_input);
        bookName = (EditText) findViewById(R.id.bookname_input);
        bookLevel = (EditText) findViewById(R.id.booklevel_input);

        issueDate = (EditText) findViewById(R.id.issuedate_input);
        issueDate.setText(DateFormat.getDateInstance().format(new Date()));
        issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(StudentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                issueDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dbController = dbController.getInstance(this);
    }

    public void btn_click(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                try {
                    dbController.insertStudent(schoolName.getText().toString(), studentName.getText().toString(), bookName.getText().toString(), bookLevel.getText().toString(), issueDate.getText().toString());
                    schoolName.setText("");
                    studentName.setText("");
                    bookName.setText("");
                    bookLevel.setText("");
                    issueDate.setText(DateFormat.getDateInstance().format(new Date()));
                    Toast.makeText(StudentActivity.this, "Record Added", Toast.LENGTH_SHORT).show();
                } catch (SQLiteException e) {
                    Toast.makeText(StudentActivity.this, "ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_list:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
