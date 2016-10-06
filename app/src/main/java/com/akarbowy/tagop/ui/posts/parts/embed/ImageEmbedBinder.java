package com.akarbowy.tagop.ui.posts.parts.embed;

import android.view.View;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.stfalcon.frescoimageviewer.ImageViewer;

public class ImageEmbedBinder implements Binder<ImageEmbedView> {
    private TagEntry tagEntry;

    public ImageEmbedBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(ImageEmbedView view) {
        view.setOnPreviewClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String viewerUrl = tagEntry.getEmbed().url;
                new ImageViewer.Builder(view.getContext(), new String[]{viewerUrl}).show();
            }
        });
    }

    @Override public void bind(ImageEmbedView view) {
        view.setPreview(tagEntry.getEmbed().previewUrl);
    }
}
