package com.bugfullabs.pharmacydb.model;

import java.sql.Date;
import java.util.List;

public class Transaction {
    private int mTranactionID;
    private List<Medication> mMedications;
    private Date mDate;
    private String mPaymentMethod;


    public Transaction(List<Medication> medications, Date date, String paymentMethod) {
        mMedications = medications;
        mDate = date;
        mPaymentMethod = paymentMethod;
    }


    public Transaction(int tranactionID, List<Medication> medications, Date date, String paymentMethod) {
        mTranactionID = tranactionID;
        mMedications = medications;
        mDate = date;
        mPaymentMethod = paymentMethod;
    }

    public int getTranactionID() {
        return mTranactionID;
    }

    public List<Medication> getMedications() {
        return mMedications;
    }

    public Date getDate() {
        return mDate;
    }

    public String getPaymentMethod() {
        return mPaymentMethod;
    }
}
