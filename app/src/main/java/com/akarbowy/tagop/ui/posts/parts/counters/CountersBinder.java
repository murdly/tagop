package com.akarbowy.tagop.ui.posts.parts.counters;

import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.comments.CommentsViewer;

public class CountersBinder implements Binder<CountersView> {

    private PostModel post;

    public CountersBinder(PostModel viewObject) {
        this.post = viewObject;
    }

    @Override public void prepare(CountersView view) {
        view.setOnCommentsClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                new CommentsViewer.Builder(view.getContext(), post.getComments()).show();
            }
        });
    }

    @Override public void bind(CountersView view) {
        view.setVotesCount(view.getContext().getString(
                R.string.post_counters_votes_param,
                post.voteCount));
        view.setCommentsCount(view.getContext().getString(
                R.string.post_counters_comments_param,
                post.commentCount));
    }

    @Override public void unbind(CountersView view) {

    }
}
