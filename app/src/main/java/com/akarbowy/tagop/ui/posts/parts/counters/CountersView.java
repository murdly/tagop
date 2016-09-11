package com.akarbowy.tagop.ui.posts.parts.counters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akarbowy.tagop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountersView extends LinearLayout {

    @BindView(R.id.text_counter_votes) TextView votesView;
    @BindView(R.id.text_counter_comments) TextView commentsView;

    public CountersView(Context context) {
        super(context);
        ButterKnife.bind(this, inflate(context, R.layout.item_post_counters, this));
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    public void setVotesCount(String count) {
        votesView.setText(count);
    }

    public void setCommentsCount(String count) {
        commentsView.setText(count);
    }
}
