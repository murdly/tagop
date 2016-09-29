package com.akarbowy.tagop.ui.posts.parts.comments;

import android.text.Spannable;
import android.text.format.DateUtils;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.ui.posts.parts.SpannableBodyBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import timber.log.Timber;

public class CommentBinder implements Binder<CommentView> {

    private final Comment comment;
    private Spannable body;
    private String relativeDate;

    public CommentBinder(Comment viewObject) {
        this.comment = viewObject;
    }

    @Override public void prepare(final CommentView view) {
        final SpannableBodyBuilder bodyBuilder = new SpannableBodyBuilder(comment.body);
        body = bodyBuilder.setOnLinkClickListener(new SpannableBodyBuilder.ClickableListener() {
            @Override public void onSpoilerClick(Spannable unspoiled) {
                body = unspoiled;
                view.setContent(body);
            }
        }).build();

        try {
            long dateInMillis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(comment.date).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateInMillis).toString();
        } catch (ParseException e) {
            relativeDate = comment.date;
            Timber.e(e, "Error when parsing date.");
        }
    }

    @Override public void bind(CommentView view) {
        view.setAvatar(comment.authorAvatar);
        view.setAuthor(comment.author);
        view.setContent(body);
        view.setVotes(comment.voteCount.toString());
        view.setDate(relativeDate);
    }
}
