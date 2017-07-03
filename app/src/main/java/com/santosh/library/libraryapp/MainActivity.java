package com.santosh.library.libraryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBController dbController;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addBtn = (Button)findViewById(R.id.add_student);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });

        dbController = dbController.getInstance(this);

        textView = (TextView) findViewById(R.id.textView);
        try {
            dbController.listStudents(textView);
        } catch (SQLiteException e) {
            Toast.makeText(MainActivity.this, "DATA NOT FOUND", Toast.LENGTH_SHORT).show();
        }
    }
}