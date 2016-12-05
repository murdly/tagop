package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class ImageEmbedPart implements SinglePartDefinition<PostModel, ImageEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_IMAGE;
    }

    @Override public Binder<ImageEmbedView> createBinder(PostModel model) {
        return new ImageEmbedBinder(model);
    }

    @Override public boolean isNeeded(PostModel model) {
        return model.getEmbed().getType().equals(EmbedsPart.IMAGE);
    }
}
