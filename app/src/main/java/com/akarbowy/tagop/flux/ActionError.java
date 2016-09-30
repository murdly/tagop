package com.akarbowy.tagop.flux;

public class ActionError {
    private final String actionType;
    private Throwable throwable;

    public ActionError(String actionType, Throwable throwable) {
       this(actionType);
        this.throwable = throwable;
    }

    public ActionError(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
