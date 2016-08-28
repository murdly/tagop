package com.akarbowy.tagop.testflux;

import com.akarbowy.tagop.flux.Action;
import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Store;
import com.squareup.otto.Subscribe;

public class TestStore extends Store {
    public static final String ID = "TestStore";
   public String value;

    public TestStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Subscribe
    @Override
    protected void onAction(Action action) {
        if (action.getType().equals("testaction")) {
            value += action.get("idvalue");
        }

        postStoreChange(new Change(ID, action));

    }
}
