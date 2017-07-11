package com.santosh.library.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    // [START declare_database_ref]
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    // [END declare_database_ref]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Button addBtn = (Button) findViewById(R.id.add_student);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });

        //Value event listener for realtime data update
        mDatabase.child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                textView.setText("");
                //iterate through each user, ignoring their UID
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) snapshot.getValue()).entrySet()) {
                    //Get user map
                    Map student = (Map) entry.getValue();
                    //String string = student.getSchoolName() + ":" + student.getStudentName() + ":" + student.getBookName() + ":" + student.getBookLevel() + student.getIssueDate() + "\n";
                    String string = student.get("schoolName") + ":" + student.get("studentName") + ":" + student.get("bookName") + ":" + student.get("bookLevel") + student.get("issueDate") + "\n";
                    //Displaying it on textView
                    textView.append(string);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // throw exception
            }
        });
    }
}