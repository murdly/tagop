package com.akarbowy.tagop;

import android.app.Application;

import com.akarbowy.tagop.flux.Dispatcher;
import com.akarbowy.tagop.flux.Flux;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Application application;
    Flux flux;

    public ApplicationModule(Flux flux, Application application) {
        this.application = application;
        this.flux = flux;
    }

    @Provides
    Dispatcher dispatcher(){
        return flux.getDispatcher();
    }

}
