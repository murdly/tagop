package com.akarbowy.tagop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.akarbowy.tagop.flux.Change;
import com.akarbowy.tagop.flux.Store;
import com.akarbowy.tagop.flux.ViewDispatch;
import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.testflux.EntryStore;
import com.akarbowy.tagop.testflux.TagopActionCreator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements ViewDispatch {

    @Inject
    EntryStore entriesStore;

    @Inject
    TagopActionCreator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TagopApplication) getApplication()).component().inject(this);
    }

    public void klik(View view) {
        creator.searchTag("android");
    }

    private void printStore() {
        ArrayList<TagEntry> entries = entriesStore.getEntries();
        for (int i = 0; i < entries.size() && i<3; i++) {
            Toast.makeText(this, entries.get(i).author, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onStoreChange(Change change) {
        switch (change.getStoreId()) {
            case EntryStore.ID:
                switch (change.getAction().getType()) {
                    case Actions.SEARCH_TAG:
                        printStore();
                        break;
                }
                break;
        }
    }

    @Override
    public List<? extends Store> getStoresToRegister() {
        return Collections.singletonList(entriesStore);
    }
}
