package com.example.secondapp.models.entities;

/**
 * Created by liran on 08/11/2017.
 */

public class Client {
    private String fname;
    private String lname;
    private long id;
    private String phoneNum;
    private String email;
    private long numCredit;

    public Client(){}
    public Client(String Fname, String Lname, long Id, String PhoneNum, String Email, long NumCredit) {
        this.fname = Fname;
        this.lname = Lname;
        this.id = Id;
        this.phoneNum = PhoneNum;
        this.email = Email;
        this.numCredit = NumCredit;
    }

    public String getFname() {return fname;}

    public void setFname(String fname) {this.fname = fname;}

    public String getLname() {return lname;}

    public void setLname(String lname) {this.lname = lname;}

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getPhoneNum() {return phoneNum;}

    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public long getNumCredit() {return numCredit;}

    public void setNumCredit(long numCredit) {this.numCredit = numCredit;}
}
