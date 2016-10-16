package com.akarbowy.tagop;

import android.app.Application;

import com.akarbowy.tagop.data.database.DatabaseHelper;
import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Flux;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module @Singleton
class ApplicationModule {

    private Application application;
    private Flux flux;

    public ApplicationModule(Flux flux, Application application) {
        this.application = application;
        this.flux = flux;
    }

    @Provides
    DatabaseHelper databaseHelper(){
        return OpenHelperManager.getHelper(application, DatabaseHelper.class);
    }

    @Provides
    Dispatcher dispatcher(){
        return flux.getDispatcher();
    }

}
