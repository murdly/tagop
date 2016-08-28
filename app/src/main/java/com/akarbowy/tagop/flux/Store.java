package com.akarbowy.tagop.flux;

public abstract class Store {

    final private Dispatcher dispatcher;

    public Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void postStoreChange(Change change){
        dispatcher.emit(change);
    }

    public void register(){
        dispatcher.register(this);
    }

    public void unregister(){
        dispatcher.unregister(this);
    }

    protected abstract void onAction(Action action);
}
