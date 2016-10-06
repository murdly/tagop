package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;

public class CommentVideoEmbedBinder implements Binder<CommentVideoEmbedView> {
    private Comment comment;

    public CommentVideoEmbedBinder(Comment model) {
        comment = model;
    }

    @Override public void prepare(CommentVideoEmbedView view) {
        view.setOnPlayVideoClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String videoUrl = comment.getEmbed().url;
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
            }
        });
    }

    @Override public void bind(CommentVideoEmbedView view) {
        view.setPreview(comment.getEmbed().previewUrl);
    }
}
