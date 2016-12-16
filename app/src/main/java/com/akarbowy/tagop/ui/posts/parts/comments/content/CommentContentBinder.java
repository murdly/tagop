package com.akarbowy.tagop.ui.posts.parts.comments.content;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.model.CommentModel;
import com.akarbowy.tagop.ui.posts.parts.SpannableBody;

public class CommentContentBinder implements Binder<CommentContentView> {

    private final CommentModel comment;
    private SpannableBody body;

    public CommentContentBinder(CommentModel model) {
        this.comment = model;
    }

    @Override public void prepare(final CommentContentView view) {
        body = new SpannableBody().setHtmlBodyText(comment.body)
                .setOnLinkClickListener(new SpannableBody.ClickableListener() {
                    @Override public void onSpoilerClick(SpannableBody unspoiled) {
                        view.setContent(unspoiled.getSpannable());
                    }
                }).create();
    }

    @Override public void bind(CommentContentView view) {
        view.setAvatar(comment.authorAvatar);
        view.setAuthor(comment.author);
        view.setContent(body.getSpannable());
    }

    @Override public void unbind(CommentContentView view) {

    }
}
