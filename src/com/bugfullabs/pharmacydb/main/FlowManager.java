package com.bugfullabs.pharmacydb.main;

public class FlowManager {
    private static FlowManager INSTANCE = new FlowManager();

    private FlowManager() {

    }

    public static FlowManager getInstance() {
        return INSTANCE;
    }
}
