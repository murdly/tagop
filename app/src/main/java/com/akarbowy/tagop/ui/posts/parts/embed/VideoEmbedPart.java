package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.SinglePartDefinition;
import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

public class VideoEmbedPart implements SinglePartDefinition<PostModel, VideoEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_VIDEO;
    }

    @Override public Binder<VideoEmbedView> createBinder(PostModel model) {
        return new VideoEmbedBinder(model);
    }

    @Override public boolean isNeeded(PostModel model) {
        return model.getEmbed().getType().equals(EmbedsPart.VIDEO);
    }
}