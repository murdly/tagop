package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class EmbedPart implements PartDefinition<TagEntry, EmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED;
    }

    @Override public Binder<EmbedView> createBinder(TagEntry viewObject) {
        return new EmbedBinder(viewObject);
    }

    @Override public boolean isNeeded(TagEntry viewObject) {
        return viewObject.embed != null && viewObject.embed.type.equals("image");
    }
}
