package com.production.dragonborn.employeeservice;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

/**
 * Created by Sidharth on 1/19/2016.
 */
public class Utility {

    public static final String SHARED_PREFERENCE_NAME = "com.production.dragonborn.employeeservice";
    public static final String USER_NAME = "com.production.dragonborn.employeeservice.user_name";
    public static final String USER_MAILID = "com.production.dragonborn.employeeservice.user_mailId";
    public static final String USER_ID_TOKEN = "com.production.dragonborn.employeeservice.user_id_token";

    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static final String ORGANISATION_JSON = "com.production.dragonborn.employeeservice.OrganisationJson";
    public static final String EMPLOYEE_JSON = "com.production.dragonborn.employeeservice.EmployeeJson";

    public static final String ORG_FILTER =
            "com.production.dragonborn.employeeservice.OrganisationJson.communication.REQUEST_PROCESSED";
    public static final String SAVE_FILTER =
            "com.production.dragonborn.employeeservice.SaveJson.communication.REQUEST_PROCESSED";
    public static final String VIEW_FILTER =
            "com.production.dragonborn.employeeservice.ViewEmployees.communication.REQUEST_PROCESSED";

    public static final String ORG_NAME_EXTRA = "com.production.dragonborn.employeeservice.ORG_NAME_EXTRA";
    public static final String FIRST_NAME_EXTRA = "com.production.dragonborn.employeeservice.FIRST_NAME_EXTRA";
    public static final String LAST_NAME_EXTRA = "com.production.dragonborn.employeeservice.LAST_NAME_EXTRA";
    public static final String EMAIL_ID_EXTRA = "com.production.dragonborn.employeeservice.EMAIL_ID_EXTRA";
    public static final String GENDER_EXTRA = "com.production.dragonborn.employeeservice.GENDER_EXTRA";
    public static final String MOBILE_EXTRA = "com.production.dragonborn.employeeservice.MOBILE_EXTRA";



    public static String getOrgEmployeeJson(String url,List<NameValuePair> pairs) {
        //final String idToken = acct.getIdToken();
        Log.d(LOG_TAG, " auth token not needed:");

        String orgEmployeeJsonString = null;


        try{
            HttpClient httpclient =   new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
//            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//            pairs.add(new BasicNameValuePair("id_token_from_Android", idToken));
//            pairs.add(new BasicNameValuePair("Gmail_Auth_Token_From_Android", "authToken"));
//            pairs.add(new BasicNameValuePair("Get_Dashboard", String.valueOf(getDashboard)));
//            pairs.add(new BasicNameValuePair("Get_Mails", String.valueOf(getMails)));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            org.apache.http.HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();

                orgEmployeeJsonString = sb.toString();
                Log.d(LOG_TAG, " data recieved from post : " + orgEmployeeJsonString);
            }

        }catch (ClientProtocolException e) {
            Log.d(LOG_TAG, " Error sending auth token : " + e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.d(LOG_TAG, " Error sending auth token : " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(LOG_TAG, " Error sending auth token : " + e);
            e.printStackTrace();
        }
        return orgEmployeeJsonString;
    }

    public static String[] getOrganisationFromJson(String mailJsonStr)
            throws JSONException {
        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.

        // file object containing attachment details

        final String ORG_SUCCESS = "success";
        final String ORG_MESSAGE = "message";
        final String ORG_ORGANISATION = "organisation";
        final String ORG_NAME = "OrgName";

        final JSONObject orgJsonObject = new JSONObject(mailJsonStr);
        int successCode = orgJsonObject.getInt(ORG_SUCCESS);
        Vector<String> organisations = new Vector<>();
        if (successCode == 1){

            JSONArray orgJsonArray = orgJsonObject.getJSONArray(ORG_ORGANISATION);
            for(int i = 0; i < orgJsonArray.length(); i++) {
                JSONObject org =orgJsonArray.getJSONObject(i);
                organisations.add(org.getString(ORG_NAME));
            }
            String[] orgNames = new String[organisations.size()];
            organisations.toArray(orgNames);
            return orgNames;
        }

        return null;
    }

    public static Vector<EmployeeDetails> getEmployeeFromJson(String mailJsonStr)
            throws JSONException {
        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.

        // file object containing attachment details

        final String ORG_SUCCESS = "success";
        final String ORG_MESSAGE = "message";
        final String ORG_EMPLOYEE = "employee";
        final String FIRST_NAME = "FirstName";
        final String LAST_NAME = "LastName";
        final String EMAIL_ID = "EmailId";
        final String GENDER = "Gender";
        final String MOBILE = "MobileNo";

        final JSONObject orgJsonObject = new JSONObject(mailJsonStr);
        int successCode = orgJsonObject.getInt(ORG_SUCCESS);
        Vector<EmployeeDetails> employees = new Vector<>();
        if (successCode == 1){

            JSONArray empJsonArray = orgJsonObject.getJSONArray(ORG_EMPLOYEE);
            for(int i = 0; i < empJsonArray.length(); i++) {
                JSONObject empObject = empJsonArray.getJSONObject(i);
                employees.add(new EmployeeDetails(
                        empObject.getString(FIRST_NAME),
                        empObject.getString(LAST_NAME),
                        empObject.getString(EMAIL_ID),
                        empObject.getString(GENDER),
                        empObject.getString(MOBILE)
                ));
            }
        }

        return employees;
    }

}
