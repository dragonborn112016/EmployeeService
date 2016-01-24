package com.production.dragonborn.employeeservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.util.Vector;

public class ViewEmployeeActivity extends AppCompatActivity {

    private static final String LOG_TAG = ViewEmployeeActivity.class.getSimpleName();

    private ListView employeeList;
    private EmloyeeListAdapter empAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        employeeList = (ListView) findViewById(R.id.Employee_list);

    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Got message");
            setListAdapter();

        }
    };

    private void setListAdapter() {
        Log.d(LOG_TAG," in set spinner: " );
        SharedPreferences sharedPreferences = this.getSharedPreferences(Utility.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String empJson = sharedPreferences.getString(Utility.EMPLOYEE_JSON, "");
        Vector<EmployeeDetails> employees ;
        try {
            employees = Utility.getEmployeeFromJson(empJson);
            Log.d(LOG_TAG," got employee data: " + employees.size() );
            empAdapter = new EmloyeeListAdapter(this,employees);
            employeeList.setAdapter(empAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            // ignore exception
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), new IntentFilter(Utility.VIEW_FILTER));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

}
