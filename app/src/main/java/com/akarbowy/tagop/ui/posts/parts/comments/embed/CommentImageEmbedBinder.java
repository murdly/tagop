package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.database.model.CommentModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

public class CommentImageEmbedBinder implements Binder<CommentImageEmbedView> {
    private CommentModel comment;

    public CommentImageEmbedBinder(CommentModel model) {
        comment = model;
    }

    @Override public void prepare(CommentImageEmbedView view) {
        view.setOnPreviewClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String viewerUrl = comment.embedModel.url;
                new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
            }
        });
    }

    @Override public void bind(CommentImageEmbedView view) {
        view.setPreview(comment.embedModel.previewUrl);
    }

    @Override public void unbind(CommentImageEmbedView view) {

    }
}
