package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.partdefiner.GroupPartDefinition;
import com.akarbowy.partdefiner.PartDefinition;
import com.akarbowy.tagop.data.network.model.TagEntry;

import java.util.ArrayList;
import java.util.List;

public class EmbedsPart implements GroupPartDefinition<TagEntry> {

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";

    @Override public boolean isNeeded(TagEntry model) {
        return model.getEmbed() != null;
    }

    @Override public List<PartDefinition<TagEntry>> getChildren(TagEntry model) {
        List<PartDefinition<TagEntry>> parts = new ArrayList<>();
        parts.add(new ImageEmbedPart());
        parts.add(new VideoEmbedPart());
        return parts;
    }
}
