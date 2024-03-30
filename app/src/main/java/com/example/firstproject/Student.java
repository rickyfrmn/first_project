package com.example.firstproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Student extends AppCompatActivity {

    private String name;
    private String rollNo;
    private String email;
    private String attendance;

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    // Getter Setters.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Constructors.
    public Student(){
        attendance = "p";
    }
}