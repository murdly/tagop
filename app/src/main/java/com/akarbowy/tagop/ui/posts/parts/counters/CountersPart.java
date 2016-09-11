package com.akarbowy.tagop.ui.posts.parts.counters;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CountersPart implements PartDefinition<TagEntry, CountersView> {
    @Override public int getViewType() {
        return ViewType.COUNTERS;
    }

    @Override public Binder<CountersView> createBinder(TagEntry viewObject) {
        return new CountersBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return true;
    }
}
