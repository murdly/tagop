package com.akarbowy.tagop.dicomponent;

import com.akarbowy.tagop.ApplicationModule;
import com.akarbowy.tagop.MainActivity;
import com.akarbowy.tagop.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by akarbowy on 07.08.16.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);

    final class Initializer {
        public static ApplicationComponent init() {
            return DaggerApplicationComponent.create();
        }
    }
}
