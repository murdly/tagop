package com.akarbowy.tagop.ui.search;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    private SearchContract.View view;

    public SearchModule(SearchContract.View view) {
        this.view = view;
    }

    @Provides SearchContract.View provideSearchContractView(){
        return view;
    }

}
