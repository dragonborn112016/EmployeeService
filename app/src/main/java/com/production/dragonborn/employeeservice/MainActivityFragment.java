package com.production.dragonborn.employeeservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public static Spinner org_spinner;
    private TextView email;
    private TextView firstName;
    private TextView lastName;
    private Button createMembers;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> orgAdapter;



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        org_spinner =(Spinner) rootView.findViewById(R.id.org_spinner);
        email =(TextView) rootView.findViewById(R.id.email_textview);
        firstName =(TextView) rootView.findViewById(R.id.first_name_textview);
        lastName =(TextView) rootView.findViewById(R.id.last_name_textview);
        createMembers = (Button) rootView.findViewById(R.id.create_members_button);


        //createMembers.setVisibility(View.GONE);

        sharedPreferences = getActivity().getSharedPreferences(Utility.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(Utility.USER_NAME, "");
        String mailId = sharedPreferences.getString(Utility.USER_MAILID, "");

        //dummy data
//        orgAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                //android.R.layout.simple_spinner_item,
//                android.R.layout.simple_dropdown_item_1line,
//                android.R.id.text1,
//                new String[]{" abc ", " abcd "}
//        );

        JsonService.startActionGetOrg(getActivity(), "", "");
//        orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        org_spinner.setAdapter(orgAdapter);
//
////        Log.d(LOG_TAG,"fine till here");
//
//        org_spinner.setOnItemSelectedListener(this);

        email.setText(mailId);
        firstName.setText(userName.split(" ")[0]);
        lastName.setText(userName.split(" ")[1]);

        return  rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Got message");
            setSpinnerAdapter();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((mMessageReceiver), new IntentFilter(Utility.ORG_FILTER));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    private void setSpinnerAdapter(){
        Log.d(LOG_TAG," in set spinner: " );
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

            createMembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EmployeeCreationActivity.class);
                    //intent.putExtra(Intent.EXTRA_TEXT, )
                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            // ignore exception
        }

    }

}
