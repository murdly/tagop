package com.akarbowy.tagop.flux;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.akarbowy.tagop.TagopApplication;
import com.squareup.otto.Bus;

import java.util.List;

public class Flux implements Application.ActivityLifecycleCallbacks {
    private static Flux instance;
    private final Dispatcher dispatcher;

    private Flux(Application application) {
        this.dispatcher = new Dispatcher(new Bus());
        application.registerActivityLifecycleCallbacks(this);
    }

    public static Flux init(TagopApplication application) {
        if (instance != null) {
            throw new IllegalStateException("Init was already called");
        }

        return instance = new Flux(application);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof ViewDispatch) {
            dispatcher.register(activity);
            List<? extends Store> storeList = ((ViewDispatch) activity).getStoresToRegister();
            for (Store store : storeList) {
                store.register();
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof ViewDispatch) {
            dispatcher.unregister(activity);
            List<? extends Store> storeList = ((ViewDispatch) activity).getStoresToRegister();
            for (Store store : storeList) {
                store.unregister();
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}