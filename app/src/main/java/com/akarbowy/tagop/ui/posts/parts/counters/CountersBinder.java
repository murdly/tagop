package com.akarbowy.tagop.ui.posts.parts.counters;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;

public class CountersBinder implements Binder<CountersView> {
    private TagEntry tagEntry;

    public CountersBinder(TagEntry viewObject) {
        this.tagEntry = viewObject;

    }

    @Override public void prepare(CountersView view) {

    }

    @Override public void bind(CountersView view) {
        view.setVotesCount(view.getContext().getString(
                R.string.post_counters_votes_param,
                tagEntry.voteCount));
        view.setCommentsCount(view.getContext().getString(
                R.string.post_counters_comments_param,
                tagEntry.commentCount)
        );
    }
}
