package com.akarbowy.tagop.ui.posts.parts.comments.footer;

import android.text.format.DateUtils;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.database.model.CommentModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;

public class CommentFooterBinder implements Binder<CommentFooterView> {

    private final CommentModel comment;
    private String relativeDate;

    public CommentFooterBinder(CommentModel model) {
        this.comment = model;
    }

    @Override public void prepare(final CommentFooterView view) {
        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(comment.date).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
        } catch (ParseException e) {
            relativeDate = comment.date;
            Timber.e(e, "Error when parsing date.");
        }
    }

    @Override public void bind(CommentFooterView view) {
        view.setVotes(comment.voteCount.toString());
        view.setDate(relativeDate);
    }

    @Override public void unbind(CommentFooterView view) {

    }
}
