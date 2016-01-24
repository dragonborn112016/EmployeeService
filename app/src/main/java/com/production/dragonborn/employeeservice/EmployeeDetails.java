package com.production.dragonborn.employeeservice;

/**
 * Created by Sidharth on 1/24/2016.
 */
public class EmployeeDetails {

    private String FIRST_NAME ;
    private String LAST_NAME ;
    private String EMAIL_ID ;
    private String GENDER ;
    private String MOBILE ;

    public EmployeeDetails(String FIRST_NAME, String LAST_NAME, String EMAIL_ID, String GENDER, String MOBILE) {
        this.FIRST_NAME = FIRST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.EMAIL_ID = EMAIL_ID;
        this.GENDER = GENDER;
        this.MOBILE = MOBILE;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }


}
