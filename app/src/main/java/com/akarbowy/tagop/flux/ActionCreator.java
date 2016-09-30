package com.akarbowy.tagop.flux;

import android.support.annotation.NonNull;

public abstract class ActionCreator {
    private final Dispatcher dispatcher;

    protected ActionCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


    protected Action newAction(@NonNull String actionId, @NonNull Object... data) {
        if (actionId.isEmpty()) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(actionId);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        return actionBuilder.build();
    }

    protected void postAction(@NonNull Action action) {
        dispatcher.dispatch(action);
    }

    protected void postError(@NonNull ActionError e) {
        dispatcher.error(e);
    }
}
