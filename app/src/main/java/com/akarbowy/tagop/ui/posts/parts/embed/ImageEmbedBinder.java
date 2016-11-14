package com.akarbowy.tagop.ui.posts.parts.embed;

import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

public class ImageEmbedBinder implements Binder<ImageEmbedView> {
    private PostModel post;

    public ImageEmbedBinder(PostModel viewObject) {
        post = viewObject;
    }

    @Override public void prepare(ImageEmbedView view) {
        view.setOnPreviewClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String viewerUrl = post.embedModel.url;
                new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
            }
        });
    }

    @Override public void bind(ImageEmbedView view) {
        view.setPreview(post.embedModel.previewUrl);
    }

    @Override public void unbind(ImageEmbedView view) {

    }
}
