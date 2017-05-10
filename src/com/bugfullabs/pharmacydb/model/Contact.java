package com.bugfullabs.pharmacydb.model;

public class Contact {
    private int mID;
    private String mEmail;
    private String mPhoneNumber;
    private String mStreet;
    private String mCity;
    private String mZipCode;

    public Contact(int ID, String email, String phoneNumber, String street, String city, String zipCode) {
        mID = ID;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mStreet = street;
        mCity = city;
        mZipCode = zipCode;
    }

    public int getID() {
        return mID;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getStreet() {
        return mStreet;
    }

    public String getCity() {
        return mCity;
    }

    public String getZipCode() {
        return mZipCode;
    }
}
