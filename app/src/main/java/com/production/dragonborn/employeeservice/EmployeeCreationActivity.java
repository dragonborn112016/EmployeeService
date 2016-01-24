package com.production.dragonborn.employeeservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EmployeeCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_creation);
        //ActionBar = this.getSupportActionBar();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
