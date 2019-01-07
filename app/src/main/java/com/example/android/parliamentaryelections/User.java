package com.example.android.parliamentaryelections;

public class User {

    public String Aadhar_No, UserName,Name, Date_Of_Birth, Mobile_No, Current_Address, Permanent_Address, Relative_Name, Relation;

    public User() {

    }

    public User(String aadhar1, String username1,String name2, String dob1,
                String mobile1, String taddress, String paddress, String rname1, String relation1)
    {
        this.Aadhar_No = aadhar1;
        this.UserName = username1;
        this.Name = name2;
        this.Date_Of_Birth = dob1;
        this.Mobile_No = mobile1;
        this.Current_Address = taddress;
        this.Permanent_Address = paddress;
        this.Relative_Name = rname1;
        this.Relation = relation1;
    }

}
