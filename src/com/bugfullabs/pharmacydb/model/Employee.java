package com.bugfullabs.pharmacydb.model;

public class Employee {
    private int mID;
    private String mName;
    private String mSurname;
    private double mSalary;
    private Contact mContact;

    public Employee(int ID, String name, String surname, double salary, Contact contact) {
        mID = ID;
        mName = name;
        mSurname = surname;
        mSalary = salary;
        mContact = contact;
    }

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getSurname() {
        return mSurname;
    }

    public double getSalary() {
        return mSalary;
    }

    public Contact getContact() {
        return mContact;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public void setSalary(double salary) {
        mSalary = salary;
    }
}
