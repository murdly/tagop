package com.akarbowy.tagop.ui.search;

import com.akarbowy.tagop.ApplicationComponent;
import com.akarbowy.tagop.utils.ActivityScoped;

import dagger.Component;

@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = SearchModule.class)
public interface SearchComponent {

    void inject(MainSearchActivity mainSearchActivity);
}
