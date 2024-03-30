package com.example.firstproject;

import android.os.Bundle;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Attendance extends AppCompatActivity {

    private ArrayList<Student> students= new ArrayList<>();
    private ListView lv;
    private StudentsAttendanceList list;
    private String courseKey;
    private String courseName;

    // Set up the menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Listener for clicks.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If the back button is pressed.
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        // Create new intent.
        Intent login = new Intent(Attendance.this, MainActivity.class);
        startActivity(login);
        // Finish currentActivity.
        finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        // Set the activity title.
        setTitle("Attendance");

        // Grab the course name from intent.
        Intent intent = getIntent();
        // Extract the name.
        courseName = intent.getStringExtra("courseName");
        final ArrayList<String> studentKeys = new ArrayList<>();

        // Grab data from database.
        // Database Reference.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("courses");

        // ListView Reference.
        lv = (ListView) findViewById(R.id.lvStudents);

        // Add Listener to fill the data.
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Boolean found = false;
                    for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                        // Grab the course name.
                        String tempCourseName = courseSnapshot.child("name").getValue().toString();
                        // Grab the course key.
                        if (tempCourseName.equals(courseName)) {
                            for (DataSnapshot subStd: courseSnapshot.child("students").getChildren()) {
                                String key = subStd.child("studentNo").getValue().toString();
                                studentKeys.add(key);
                            }
                            found = true;
                        }
                        // Break when key is found.
                        if (found) break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        // Finally grab the students.
        // Build Query for the table.
        final Query stdQuery = database.getReference("students");

        stdQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    // Grab the key.
                    String stdKey = courseSnapshot.getKey();
                    // Match the key.
                    if (studentKeys.contains(stdKey)) {
                        // Grab the student.
                        Student std = courseSnapshot.getValue(Student.class);
                        // Add to the list.
                        students.add(std);
                    }
                }
                // Creating object of custom view item.
                list = new StudentsAttendanceList(Attendance.this, students);
                lv.setAdapter(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });


        // Add Listener to search the data.
        // Grab the edit text view.
        final EditText editTxt = (EditText)findViewById(R.id.etSearch);
        editTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Grab radio button to identify search type.
                RadioGroup rg = (RadioGroup)findViewById(R.id.rgSearch);
                // Grab the value of radio button.
                String value = ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                        .getText().toString();
                // Get the Search Query.
                String text = editTxt.getText().toString().toLowerCase(Locale.getDefault());

                // Check the search type.
                if (value.equals("Name")) {
                    list.filterByName(text);
                } else {
                    list.filterByRollNo(text);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}