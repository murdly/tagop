package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;

public class EmbedBinder implements Binder<EmbedView> {
    TagEntry tagEntry;

    public EmbedBinder(TagEntry viewObject) {
        tagEntry = viewObject;
    }

    @Override public void prepare(EmbedView view) {

    }

    @Override public void bind(EmbedView view) {
        view.setPreview(tagEntry.getEmbed().previewUrl);
        view.setViewerUrl(tagEntry.getEmbed().url);
    }
}
