package com.akarbowy.tagop.ui.posts.parts.comments.footer;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akarbowy.tagop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentFooterView extends LinearLayout {

    @BindView(R.id.text_date) TextView dateView;
    @BindView(R.id.text_counter_votes) TextView votesView;

    public CommentFooterView(Context context) {
        super(context);
        ButterKnife.bind(this, inflate(context, R.layout.item_comment_footer, this));
    }

    public void setDate(String date) {
        dateView.setText(date);
    }

    public void setVotes(String votes) {
        votesView.setText(votes);
    }

}
