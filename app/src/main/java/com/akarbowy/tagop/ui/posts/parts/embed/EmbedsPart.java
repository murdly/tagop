package com.akarbowy.tagop.ui.posts.parts.embed;

import com.akarbowy.partdefiner.GroupPartDefinition;
import com.akarbowy.partdefiner.PartDefinition;
import com.akarbowy.tagop.data.database.model.PostModel;

import java.util.ArrayList;
import java.util.List;

public class EmbedsPart implements GroupPartDefinition<PostModel> {

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";

    @Override public boolean isNeeded(PostModel model) {
        return model.embedModel != null;
    }

    @Override public List<PartDefinition<PostModel>> getChildren(PostModel model) {
        List<PartDefinition<PostModel>> parts = new ArrayList<>();
        parts.add(new ImageEmbedPart());
        parts.add(new VideoEmbedPart());
        return parts;
    }
}
