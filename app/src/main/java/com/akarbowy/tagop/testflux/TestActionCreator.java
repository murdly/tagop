package com.akarbowy.tagop.testflux;

import com.akarbowy.tagop.flux.ActionCreator;
import com.akarbowy.tagop.flux.Dispatcher;

public class TestActionCreator extends ActionCreator {

    public TestActionCreator(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public void addItem(String value) {
        if (value.isEmpty()) {
            return;
        }
        postAction(newAction("testaction", "idvalue", value));
    }

}
