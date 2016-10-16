package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.network.model.Comment;
import com.stfalcon.frescoimageviewer.ImageViewer;

public class CommentImageEmbedBinder implements Binder<CommentImageEmbedView> {
    private Comment comment;

    public CommentImageEmbedBinder(Comment model) {
        comment = model;
    }

    @Override public void prepare(CommentImageEmbedView view) {
        view.setOnPreviewClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String viewerUrl = comment.getEmbed().url;
                new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
            }
        });
    }

    @Override public void bind(CommentImageEmbedView view) {
        view.setPreview(comment.getEmbed().previewUrl);
    }

    @Override public void unbind(CommentImageEmbedView view) {

    }
}
