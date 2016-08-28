package com.akarbowy.tagop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.network.WykopService;
import com.akarbowy.tagop.testflux.TestActionCreator;
import com.akarbowy.tagop.testflux.TestStore;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ViewDispatch {

    @Inject
    WykopService service;

    @Inject
    TestStore thestore;
    @Inject
    TestActionCreator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TagopApplication) getApplication()).component().inject(this);

        /*service.getTag("android", 1)
                .enqueue(new Callback<QueryResult>() {
                    @Override
                    public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
                        Timber.i("response code %s", response.code());
                    }

                    @Override
                    public void onFailure(Call<QueryResult> call, Throwable t) {
                        Timber.e("fail", t);
                    }
                });*/
    }

    public void klik(View view){
        creator.addItem("jak mnie slychac");
    }

    @Subscribe
    public void testStoreChange(Change change){
        switch (change.getStoreId()) {
            case TestStore.ID:
                Timber.i("a mnie", thestore.value);
                break;
        }
    }

    @Override
    public List<? extends Store> getStoresToRegister() {
        return Collections.singletonList(thestore);
    }
}
