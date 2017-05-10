package com.bugfullabs.pharmacydb.model;

public class Medication {
    private int mMedicationID;
    private String mName;
    private String mAmount;
    private String mType;
    private boolean mPrescription;
    private double mRefundation;
    private String mDescription;
    private double mBulkCost;

    private double mStockPrice;
    private int mStockAmount;
    private String mStockLocation;

    public Medication(int medicationID, String name, String amount, String type, boolean prescription, double refundation,
                      String description, double bulkCost, double stockPrice, int stockAmount, String stockLocation) {
        mMedicationID = medicationID;
        mName = name;
        mAmount = amount;
        mType = type;
        mPrescription = prescription;
        mRefundation = refundation;
        mDescription = description;
        mBulkCost = bulkCost;
        mStockPrice = stockPrice;
        mStockAmount = stockAmount;
        mStockLocation = stockLocation;
    }

    public int getMedicationID() {
        return mMedicationID;
    }

    public String getName() {
        return mName;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getType() {
        return mType;
    }

    public boolean isPrescription() {
        return mPrescription;
    }

    public double getRefundation() {
        return mRefundation;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getBulkCost() {
        return mBulkCost;
    }

    public double getStockPrice() {
        return mStockPrice;
    }

    public int getStockAmount() {
        return mStockAmount;
    }

    public String getStockLocation() {
        return mStockLocation;
    }
}
