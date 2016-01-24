package com.production.dragonborn.employeeservice;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmployeeCreationActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = EmployeeCreationActivityFragment.class.getSimpleName();
    private String organisation;

    private Spinner org_spinner;
    private ArrayAdapter<String> orgAdapter;

    private EditText firstName;
    private EditText lastName;
    private EditText emailId;
    private EditText mobileNo;
    private EditText gender;
    private Button save;
    private Button viewList;

    public EmployeeCreationActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee_creation, container, false);

        org_spinner = (Spinner) rootView.findViewById(R.id.org_spinner_employee);
        firstName = (EditText)rootView.findViewById(R.id.firstName_employee);
        lastName = (EditText)rootView.findViewById(R.id.lastName_employee);
        emailId = (EditText)rootView.findViewById(R.id.email_employee);
        mobileNo = (EditText)rootView.findViewById(R.id.phone_employee);
        gender = (EditText)rootView.findViewById(R.id.Gender_employee);

        save = (Button)rootView.findViewById(R.id.save_members_button);
        viewList = (Button)rootView.findViewById(R.id.view_members_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validForm()) {

                    JsonService.startActionSaveMember(getActivity(),
                            organisation,
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            gender.getText().toString(),
                            emailId.getText().toString(),
                            mobileNo.getText().toString());
                }
            }
        });
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonService.startActionViewMember(getActivity(),organisation,"");
                Intent intent = new Intent(getActivity(),ViewEmployeeActivity.class);
                intent.putExtra(Utility.ORG_NAME_EXTRA,organisation);
                startActivity(intent);
            }
        });

        setSpinnerAdapter();

        return rootView;
    }

    private boolean validForm() {

        boolean result = true;
        removeErrors();
        final String REQUIRED = "* Required";
        try {
            if (firstName.getText().toString().isEmpty()) {
                firstName.setError(REQUIRED);
                result = false;
            }
            if (lastName.getText().toString().isEmpty()) {
                lastName.setError(REQUIRED);
                result = false;
            }
            if (mobileNo.getText().toString().isEmpty()) {
                mobileNo.setError(REQUIRED);
                result = false;
            }
            if (gender.getText().toString().isEmpty()) {
                gender.setError(REQUIRED);
                result = false;
            }
            if (emailId.getText().toString().isEmpty()) {
                emailId.setError(REQUIRED);
                result = false;
            } else if (emailId.getText().toString().split("@").length == 1) {
                emailId.setError("Incorrect Email");
                result = false;
            } else if (emailId.getText().toString().split("@")[1].split("\\.").length == 1) {
                emailId.setError("Incorrect Email");
                result = false;
            }
        }catch(Exception e){
            Log.d(LOG_TAG," error :" + e);
            result = false;
        }

        return result;
    }

    private void removeErrors() {
        firstName.setError(null);
        lastName.setError(null);
        emailId.setError(null);
        mobileNo.setError(null);
        gender.setError(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        organisation = parent.getItemAtPosition(position).toString();
//        Toast.makeText(getActivity(), organisation,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setSpinnerAdapter(){
        Log.d(LOG_TAG, " in set spinner: ");
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Utility.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String orgJson = sharedPreferences.getString(Utility.ORGANISATION_JSON, "");
        String[] organisation = new String[0];
        try {
            organisation = Utility.getOrganisationFromJson(orgJson);
            Log.d(LOG_TAG," got organisation data: " + organisation.length );
            orgAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    //android.R.layout.simple_spinner_item,
                    android.R.layout.simple_dropdown_item_1line,
                    android.R.id.text1,
                    organisation
            );

            orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            org_spinner.setAdapter(orgAdapter);
            org_spinner.setOnItemSelectedListener(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
