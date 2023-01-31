package com.example.employeedetails;

public class MainModel {

    String name,department,gender,email,address,eurl;

    MainModel()
    {

    }

    public MainModel(String name, String department, String gender, String email, String address, String eurl) {
        this.name = name;
        this.department = department;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.eurl = eurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEurl() {
        return eurl;
    }

    public void setEurl(String eurl) {
        this.eurl = eurl;
    }
}
