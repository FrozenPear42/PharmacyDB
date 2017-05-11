package com.bugfullabs.pharmacydb.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Transaction {
    private int mTransactionID;
    private List<Medication> mMedications;
    private Map<Medication, Integer> mMadicationQuantity;
    private Date mDate;
    private String mPaymentMethod;


    public Transaction(int transactionID, List<Medication> medications, Map<Medication, Integer> madicationQuantity, Date date, String paymentMethod) {
        mTransactionID = transactionID;
        mMedications = medications;
        mMadicationQuantity = madicationQuantity;
        mDate = date;
        mPaymentMethod = paymentMethod;
    }

    public Transaction(List<Medication> medications, Map<Medication, Integer> madicationQuantity, Date date, String paymentMethod) {
        mMedications = medications;
        mMadicationQuantity = madicationQuantity;
        mDate = date;
        mPaymentMethod = paymentMethod;
    }

    public int getTransactionID() {
        return mTransactionID;
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

    public int getQuantityOf(Medication medication) {
        return mMadicationQuantity.get(medication);
    }
}
