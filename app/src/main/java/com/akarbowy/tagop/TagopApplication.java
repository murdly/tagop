package com.akarbowy.tagop;

import android.app.Application;

import com.akarbowy.tagop.dicomponent.ApplicationComponent;
import com.akarbowy.tagop.dicomponent.DaggerApplicationComponent;
import com.akarbowy.tagop.flux.Flux;

import timber.log.Timber;

public class TagopApplication extends Application {

    private ApplicationComponent component;
    private Flux flux;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new TagopDebugTree());
        }

        flux = Flux.init(this);
        component = DaggerApplicationComponent.Initializer.init(flux, this);
    }

    public ApplicationComponent component() {
        return component;
    }


    private class TagopDebugTree extends Timber.DebugTree {
        @Override
        protected String createStackElementTag(StackTraceElement element) {
            return String.format("%s-%s", super.createStackElementTag(element),
                    element.getMethodName());
        }
    }

}
