package com.akarbowy.tagop.ui.posts.parts.embed;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;

public class VideoEmbedBinder implements Binder<VideoEmbedView> {
    private TagEntry tagEntry;

    public VideoEmbedBinder(TagEntry model) {
        tagEntry = model;
    }

    @Override public void prepare(VideoEmbedView view) {
        view.setOnPlayVideoClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String videoUrl = tagEntry.getEmbed().url;
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
            }
        });
    }

    @Override public void bind(VideoEmbedView view) {
        view.setPreview(tagEntry.getEmbed().previewUrl);
    }
}
