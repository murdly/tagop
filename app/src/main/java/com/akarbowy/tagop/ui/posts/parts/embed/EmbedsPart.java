package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.GroupPartDefinition;
import com.akarbowy.tagop.parto.PartDefinition;

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
