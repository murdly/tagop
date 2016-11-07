package com.akarbowy.tagop;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext(){
        return context;
    }

}
