package com.santosh.library.libraryapp;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DBController controller;
    EditText studentName, schoolName, bookName, bookLevel, issueDate;
    DatePickerDialog datePickerDialog;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        studentName = (EditText) findViewById(R.id.studentname_input);
        schoolName = (EditText) findViewById(R.id.schoolname_input);
        bookName = (EditText) findViewById(R.id.bookname_input);
        bookLevel = (EditText) findViewById(R.id.booklevel_input);

        issueDate = (EditText) findViewById(R.id.issuedate_input);
        issueDate.setText( DateFormat.getDateInstance().format(new Date()) );
        issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
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

        textView = (TextView) findViewById(R.id.textView);

        controller = new DBController(this, "", null, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btn_click(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                try {
                    controller.insertStudent(studentName.getText().toString(), schoolName.getText().toString(), bookName.getText().toString(), bookLevel.getText().toString(), issueDate.getText().toString());
                } catch (SQLiteException e) {
                    Toast.makeText(MainActivity.this, "ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_delete:
                if (controller.findStudent(studentName.getText().toString())) {
                    controller.deleteStudent(studentName.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "STUDENT NOT FOUND", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_update:
                break;
            case R.id.button_list:
                try {
                    controller.listStudents(textView);
                } catch (SQLiteException e) {
                    Toast.makeText(MainActivity.this, "DATA NOT FOUND", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
