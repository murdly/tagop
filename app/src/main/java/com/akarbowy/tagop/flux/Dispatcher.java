package com.akarbowy.tagop.flux;

import com.squareup.otto.Bus;

public class Dispatcher {

    private Bus bus;

    public Dispatcher(Bus bus) {
        this.bus = bus;
    }

    public void register(Object clazz) {
        bus.register(clazz);
    }

    public void unregister(Object clazz) {
        bus.unregister(clazz);
    }

    public void dispatch(Action action) {
        bus.post(action);
    }

    public void emit(Change event) {
        bus.post(event);
    }

}
