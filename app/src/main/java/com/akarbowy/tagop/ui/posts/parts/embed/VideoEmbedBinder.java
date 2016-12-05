package com.akarbowy.tagop.ui.posts.parts.embed;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.tagop.data.database.model.PostModel;

public class VideoEmbedBinder implements Binder<VideoEmbedView> {
    private PostModel post;

    public VideoEmbedBinder(PostModel model) {
        post = model;
    }

    @Override public void prepare(VideoEmbedView view) {
        view.setOnPlayVideoClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String videoUrl = post.getEmbed().getUrl();
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
            }
        });
    }

    @Override public void bind(VideoEmbedView view) {
        view.setPreview(post.getEmbed().getPreviewUrl());
    }

    @Override public void unbind(VideoEmbedView view) {

    }
}
