package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.SinglePartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;
import com.akarbowy.tagop.ui.posts.parts.embed.EmbedsPart;
import com.akarbowy.tagop.ui.posts.parts.embed.VideoEmbedBinder;

public class CommentVideoEmbedPart implements SinglePartDefinition<Comment, CommentVideoEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_VIDEO;
    }

    @Override public Binder<CommentVideoEmbedView> createBinder(Comment model) {
        return new CommentVideoEmbedBinder(model);
    }

    @Override public boolean isNeeded(Comment model) {
        return model.getEmbed().type.equals(EmbedsPart.VIDEO);
    }
}