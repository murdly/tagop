package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import com.akarbowy.partdefiner.GroupPartDefinition;
import com.akarbowy.partdefiner.PartDefinition;
import com.akarbowy.tagop.data.database.model.CommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentEmbedsPart implements GroupPartDefinition<CommentModel> {

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";

    @Override public boolean isNeeded(CommentModel model) {
        return model.getEmbed() != null;
    }

    @Override public List<PartDefinition<CommentModel>> getChildren(CommentModel model) {
        List<PartDefinition<CommentModel>> parts = new ArrayList<>();
        parts.add(new CommentImageEmbedPart());
        parts.add(new CommentVideoEmbedPart());
        return parts;
    }
}
