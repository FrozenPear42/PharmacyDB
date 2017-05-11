package com.bugfullabs.pharmacydb.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Transaction {
    private int mTransactionID;
    private double mTotal;
    private List<Medication> mMedications;
    private Map<Medication, Integer> mMedicationQuantity;
    private Date mDate;
    private String mPaymentMethod;

    public Transaction(int transactionID, double total, List<Medication> medications,
                       Map<Medication, Integer> medicationQuantity, Date date, String paymentMethod) {
        mTransactionID = transactionID;
        mTotal = total;
        mMedications = medications;
        mMedicationQuantity = medicationQuantity;
        mDate = date;
        mPaymentMethod = paymentMethod;
    }

    public Transaction(double total, List<Medication> medications,
                       Map<Medication, Integer> medicationQuantity, Date date, String paymentMethod) {
        mTotal = total;
        mMedications = medications;
        mMedicationQuantity = medicationQuantity;
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
        return mMedicationQuantity.get(medication);
    }

    public double getTotal() {
        return mTotal;
    }

    public Map<Medication, Integer> getMedicationsQuantity() {
        return mMedicationQuantity;
    }
}
