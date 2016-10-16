package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.network.model.TagEntry;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class VideoEmbedPart implements SinglePartDefinition<TagEntry, VideoEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_VIDEO;
    }

    @Override public Binder<VideoEmbedView> createBinder(TagEntry model) {
        return new VideoEmbedBinder(model);
    }

    @Override public boolean isNeeded(TagEntry model) {
        return model.getEmbed().type.equals(EmbedsPart.VIDEO);
    }
}