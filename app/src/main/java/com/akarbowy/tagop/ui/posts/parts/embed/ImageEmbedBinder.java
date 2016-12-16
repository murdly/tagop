package com.akarbowy.tagop.ui.posts.parts.embed;

import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.model.PostModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

public class ImageEmbedBinder implements Binder<ImageEmbedView> {
    private PostModel post;

    public ImageEmbedBinder(PostModel viewObject) {
        post = viewObject;
    }

    @Override public void prepare(ImageEmbedView view) {
        view.setOnPreviewClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String viewerUrl = post.getEmbed().getUrl();
                new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
            }
        });
    }

    @Override public void bind(ImageEmbedView view) {
        view.setPreview(post.getEmbed().getPreviewUrl());
    }

    @Override public void unbind(ImageEmbedView view) {

    }
}
