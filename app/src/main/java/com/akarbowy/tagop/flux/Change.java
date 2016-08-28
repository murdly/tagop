package com.akarbowy.tagop.flux;

public class Change {
    private String storeId;
    private Action action;

    public Change(String storeId, Action action) {
        this.storeId = storeId;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public String getStoreId() {
        return storeId;
    }
}
