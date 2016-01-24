package com.production.dragonborn.employeeservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by Sidharth on 1/24/2016.
 */

public class EmloyeeListAdapter extends BaseAdapter {

    private Context context;
    private Vector<EmployeeDetails> employees;
    private  static LayoutInflater inflater = null;
    private EmployeeDetails employeeDetails;

    public EmloyeeListAdapter(Context context, Vector<EmployeeDetails> employees) {
        this.context = context;
        this.employees = employees;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{

        public TextView firstName;
        public TextView lastName;
        public TextView gender;
        public TextView emailId;
        public TextView mobile;

    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.empoyee_list_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.firstName = (TextView) vi.findViewById(R.id.firstName_employee_list_item);
            holder.lastName = (TextView) vi.findViewById(R.id.lastName_employee_list_item);
            holder.gender = (TextView) vi.findViewById(R.id.Gender_employee_list_item);
            holder.emailId = (TextView) vi.findViewById(R.id.email_employee_list_item);
            holder.mobile = (TextView) vi.findViewById(R.id.phone_employee_list_item);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        employeeDetails = employees.get(position);
        if (employeeDetails!=null){
            holder.firstName.setText(employeeDetails.getFIRST_NAME());
            holder.lastName.setText(employeeDetails.getLAST_NAME());
            holder.gender.setText(employeeDetails.getGENDER());
            holder.emailId.setText(employeeDetails.getEMAIL_ID());
            holder.mobile.setText(employeeDetails.getMOBILE());
        }


        return vi;

    }


}
