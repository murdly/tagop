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

    @Override public void prepare(final ImageEmbedView view) {
        view.setOnPreviewClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String url = post.getEmbed().getUrl();

                if (post.getEmbed().isGif()) {
                    view.startGif(url);
                } else {
                    new ImageViewer.Builder<>(view.getContext(), new String[]{url})
                            .show();
                }
            }
        });
    }

    @Override public void bind(ImageEmbedView view) {
        view.setPreviewUrl(post.getEmbed().getPreviewUrl());
    }

    @Override public void unbind(ImageEmbedView view) {

    }
}
