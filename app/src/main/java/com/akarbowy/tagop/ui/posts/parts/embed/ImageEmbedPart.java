package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.SinglePartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class ImageEmbedPart implements SinglePartDefinition<TagEntry, ImageEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_IMAGE;
    }

    @Override public Binder<ImageEmbedView> createBinder(TagEntry model) {
        return new ImageEmbedBinder(model);
    }

    @Override public boolean isNeeded(TagEntry model) {
        return model.getEmbed().type.equals(EmbedsPart.IMAGE);
    }
}
