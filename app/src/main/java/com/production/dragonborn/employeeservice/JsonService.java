package com.production.dragonborn.employeeservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class JsonService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ORG = "com.production.dragonborn.employeeservice.action.GET_ORG";
    private static final String ACTION_SAVE_EMPLOYEE = "com.production.dragonborn.employeeservice.action.SAVE_EMPLOYEE";
    private static final String ACTION_VIEW_EMPLOYEE = "com.production.dragonborn.employeeservice.action.VIEW_EMPLOYEE";

    private static final String EXTRA_PARAM1 = "com.production.dragonborn.employeeservice.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.production.dragonborn.employeeservice.extra.PARAM2";
    private static final String LOG_TAG = JsonService.class.getSimpleName();
    private static final String ORG_URL = "http://dragonborn.net16.net/comp1.php";
    private static final String SAVE_URL = "http://dragonborn.net16.net/save.php";
    private static final String EMPLOYEE_URL = "http://dragonborn.net16.net/display1.php";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionGetOrg(Context context, String param1, String param2) {
        Intent intent = new Intent(context, JsonService.class);
        intent.setAction(ACTION_GET_ORG);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSaveMember(Context context, String orgName,String fName, String lName,String gender,String email,String mobile) {
        Intent intent = new Intent(context, JsonService.class);
        intent.setAction(ACTION_SAVE_EMPLOYEE);
        intent.putExtra(Utility.ORG_NAME_EXTRA, orgName);
        intent.putExtra(Utility.FIRST_NAME_EXTRA, fName);
        intent.putExtra(Utility.LAST_NAME_EXTRA, lName);
        intent.putExtra(Utility.GENDER_EXTRA, gender);
        intent.putExtra(Utility.EMAIL_ID_EXTRA, email);
        intent.putExtra(Utility.MOBILE_EXTRA, mobile);
        context.startService(intent);
    }

    public static void startActionViewMember(Context context, String param1, String param2) {
        Intent intent = new Intent(context, JsonService.class);
        intent.setAction(ACTION_VIEW_EMPLOYEE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public JsonService() {
        super("JsonService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ORG.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionGetOrg(param1, param2);
            } else if (ACTION_SAVE_EMPLOYEE.equals(action)) {
                final String orgName = intent.getStringExtra(Utility.ORG_NAME_EXTRA);
                final String fName = intent.getStringExtra(Utility.FIRST_NAME_EXTRA);
                final String lName = intent.getStringExtra(Utility.LAST_NAME_EXTRA);
                final String gender = intent.getStringExtra(Utility.GENDER_EXTRA);
                final String email = intent.getStringExtra(Utility.EMAIL_ID_EXTRA);
                final String mobile = intent.getStringExtra(Utility.MOBILE_EXTRA);
                handleActionSaveEmployee(orgName, fName, lName, gender, email, mobile);
            }else if (ACTION_VIEW_EMPLOYEE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionViewEmployee(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetOrg(String param1, String param2) {
        //192.168.1.102/comp1.php

        SharedPreferences sharedPreferences = this.getSharedPreferences(Utility.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String idToken = sharedPreferences.getString(Utility.USER_ID_TOKEN, "");
        Log.d(LOG_TAG, " user id token :" + idToken);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("id_token_from_Android", idToken));
        String orgJson = Utility.getOrgEmployeeJson(ORG_URL,pairs);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utility.ORGANISATION_JSON, orgJson);
        editor.apply();
        Intent intent = new Intent(Utility.ORG_FILTER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

//        try {
//
//            String[] organisation = getOrganisationFromJson(orgJson);
//            ArrayList<String> entries =
//                    new ArrayList<String>(Arrays.asList(organisation));
//
////            Utility.orgAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
////            Utility.orgAdapter.clear();
//            Utility.orgAdapter.add("hello");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }catch (UnsupportedOperationException e){
//            e.printStackTrace();
//        }

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSaveEmployee(String orgName, String fName, String lName,String gender,String email,String mobile) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Utility.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String idToken = sharedPreferences.getString(Utility.USER_ID_TOKEN, "");
        Log.d(LOG_TAG, " user id token :" + idToken);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("id_token_from_Android", idToken));
        pairs.add(new BasicNameValuePair("OrgName", orgName));
        pairs.add(new BasicNameValuePair("FirstName", fName));
        pairs.add(new BasicNameValuePair("LastName", lName));
        pairs.add(new BasicNameValuePair("Gender", gender));
        pairs.add(new BasicNameValuePair("MobileNo", mobile));
        pairs.add(new BasicNameValuePair("EmailId", email));
        String orgJson = Utility.getOrgEmployeeJson(SAVE_URL,pairs);
        try {
            final JSONObject savedObject = new JSONObject(orgJson);
            int status = savedObject.getInt("success");
            Log.d(LOG_TAG, " status of save :" + status);
            if(status ==1){
                Handler h = new Handler(getMainLooper());
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(JsonService.this,"Record Saved",Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                Handler h = new Handler(getMainLooper());
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(JsonService.this,savedObject.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void handleActionViewEmployee(String orgName, String param2) {

        SharedPreferences sharedPreferences = this.getSharedPreferences(Utility.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        final String idToken = sharedPreferences.getString(Utility.USER_ID_TOKEN, "");
        Log.d(LOG_TAG, " user id token :" + idToken);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("id_token_from_Android", idToken));
        pairs.add(new BasicNameValuePair("OrgName", orgName));
        String orgJson = Utility.getOrgEmployeeJson(EMPLOYEE_URL,pairs);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utility.EMPLOYEE_JSON, orgJson);
        editor.apply();
        Intent intent = new Intent(Utility.VIEW_FILTER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}
