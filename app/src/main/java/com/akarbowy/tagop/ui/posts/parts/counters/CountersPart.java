package com.akarbowy.tagop.ui.posts.parts.counters;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class CountersPart implements SinglePartDefinition<PostModel, CountersView> {
    @Override public int getViewType() {
        return ViewType.COUNTERS;
    }

    @Override public Binder<CountersView> createBinder(PostModel model) {
        return new CountersBinder(model);
    }

    @Override public boolean isNeeded(PostModel model) {
        return true;
    }
}
