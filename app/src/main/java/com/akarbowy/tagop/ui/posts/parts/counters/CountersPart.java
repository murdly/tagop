package com.akarbowy.tagop.ui.posts.parts.counters;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.network.model.TagEntry;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CountersPart implements SinglePartDefinition<TagEntry, CountersView> {
    @Override public int getViewType() {
        return ViewType.COUNTERS;
    }

    @Override public Binder<CountersView> createBinder(TagEntry viewObject) {
        return new CountersBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry model) {
        return true;
    }
}
