package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.GroupPartDefinition;
import com.akarbowy.tagop.parto.PartDefinition;
import com.akarbowy.tagop.ui.posts.parts.embed.ImageEmbedPart;
import com.akarbowy.tagop.ui.posts.parts.embed.VideoEmbedPart;

import java.util.ArrayList;
import java.util.List;

public class CommentEmbedsPart implements GroupPartDefinition<Comment> {

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";

    @Override public boolean isNeeded(Comment model) {
        return model.getEmbed() != null;
    }

    @Override public List<PartDefinition<Comment>> getChildren(Comment model) {
        List<PartDefinition<Comment>> parts = new ArrayList<>();
        parts.add(new CommentImageEmbedPart());
        parts.add(new CommentVideoEmbedPart());
        return parts;
    }
}
