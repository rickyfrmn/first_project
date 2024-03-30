package com.example.firstproject;

import android.os.Bundle;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ReportDashboard extends AppCompatActivity {

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
        Intent login = new Intent(ReportDashboard.this, MainActivity.class);
        startActivity(login);
        // Finish currentActivity.
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dashboard);
        // Set the title.
        setTitle("Reports Dashboard");

        // Set onClick Listeners for the CardViews.
        // For Course Report
        ConstraintLayout cvCourseReport = (ConstraintLayout) findViewById(R.id.cvCourseReport);
        cvCourseReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent.
                Intent intent = new Intent(ReportDashboard.this, CourseReport.class);
                // Grab the extra from previous intent.
                String item = getIntent().getStringExtra("courseName");
                // Attach the string to new intent.
                intent.putExtra("courseName", item);
                // Start the new activity.
                startActivity(intent);
            }
        });

        // For Student Report
        ConstraintLayout cvStudentReport = (ConstraintLayout) findViewById(R.id.cvStudentReport);
        cvStudentReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent.
                Intent intent = new Intent(ReportDashboard.this, StudentReport.class);
                // Grab the extra from previous intent.
                String item = getIntent().getStringExtra("courseName");
                // Attach the string to new intent.
                intent.putExtra("courseName", item);
                // Start the new activity.
                startActivity(intent);
            }
        });

        // For Daily Report
        ConstraintLayout cvDailyReport = (ConstraintLayout) findViewById(R.id.cvDailyReport);
        cvDailyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new intent.
                Intent intent = new Intent(ReportDashboard.this, DailyReport.class);
                // Grab the extra from previous intent.
                String item = getIntent().getStringExtra("courseName");
                // Attach the string to new intent.
                intent.putExtra("courseName", item);
                // Start the new activity.
                startActivity(intent);
            }
        });
    }
}