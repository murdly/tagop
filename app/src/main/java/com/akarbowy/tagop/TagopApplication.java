package com.akarbowy.tagop;

import android.app.Application;

import com.akarbowy.tagop.flux.Flux;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class TagopApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new TagopDebugTree());
//            AndroidDevMetrics.initWith(this);
            Stetho.initializeWithDefaults(this);
        }

        Flux flux = Flux.init(this);
        component = DaggerApplicationComponent.Initializer.init(flux, this);
        Fresco.initialize(this);
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
