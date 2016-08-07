package com.akarbowy.tagop;

import android.app.Application;

import com.akarbowy.tagop.dicomponent.ApplicationComponent;
import com.akarbowy.tagop.dicomponent.DaggerApplicationComponent;

import timber.log.Timber;

/**
 * Created by akarbowy on 07.08.16.
 */
public class TagopApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

       component = DaggerApplicationComponent.Initializer.init();
    }

    public ApplicationComponent component(){
        return component;
    }

}
