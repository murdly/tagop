package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.database.model.CommentModel;

public class CommentVideoEmbedBinder implements Binder<CommentVideoEmbedView> {
    private CommentModel comment;

    public CommentVideoEmbedBinder(CommentModel model) {
        comment = model;
    }

    @Override public void prepare(CommentVideoEmbedView view) {
        view.setOnPlayVideoClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String videoUrl = comment.embedModel.url;
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
            }
        });
    }

    @Override public void bind(CommentVideoEmbedView view) {
        view.setPreview(comment.embedModel.previewUrl);
    }

    @Override public void unbind(CommentVideoEmbedView view) {

    }
}
