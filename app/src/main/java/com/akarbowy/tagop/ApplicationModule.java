package com.akarbowy.tagop;

import android.app.Application;

import com.akarbowy.tagop.flux.Flux;
import com.akarbowy.tagop.testflux.TestActionCreator;
import com.akarbowy.tagop.testflux.TestStore;

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
    TestStore testStore(){
        return new TestStore(flux.getDispatcher());
    }

    @Provides
    TestActionCreator testActionCreator(){
        return new TestActionCreator(flux.getDispatcher());
    }
}
