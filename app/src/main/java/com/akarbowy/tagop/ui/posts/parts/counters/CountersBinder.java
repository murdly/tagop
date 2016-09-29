package com.akarbowy.tagop.ui.posts.parts.counters;

import android.view.View;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.ui.posts.parts.comments.CommentsViewer;

public class CountersBinder implements Binder<CountersView> {

    private TagEntry tagEntry;

    public CountersBinder(TagEntry viewObject) {
        this.tagEntry = viewObject;
    }

    @Override public void prepare(CountersView view) {
        view.setOnCommentsClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                new CommentsViewer.Builder(view.getContext(), tagEntry.comments).show();
            }
        });
    }

    @Override public void bind(CountersView view) {
        view.setVotesCount(view.getContext().getString(
                R.string.post_counters_votes_param,
                tagEntry.voteCount));
        view.setCommentsCount(view.getContext().getString(
                R.string.post_counters_comments_param,
                tagEntry.commentCount));
    }
}
