package com.akarbowy.tagop.ui.posts.parts.counters.comments;

import android.text.format.DateUtils;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;

public class CommentBinder implements Binder<CommentView> {

    private final Comment comment;

    public CommentBinder(Comment viewObject) {
        this.comment = viewObject;
    }

    @Override public void prepare(CommentView view) {

    }

    @Override public void bind(CommentView view) {
        view.setAvatar(comment.authorAvatar);
        view.setAuthor(comment.author);
        view.setContent(comment.body);
        view.setVotes(comment.voteCount.toString());

        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(comment.date).getTime();
            String relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
            view.setDate(relativeDate);
        } catch (ParseException e) {
            Timber.e(e, "Error when parsing date.");
        }
    }
}
