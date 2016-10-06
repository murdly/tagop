package com.akarbowy.tagop.ui.posts.parts.comments.embed;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.Binder;
import com.akarbowy.tagop.parto.SinglePartDefinition;
import com.akarbowy.tagop.ui.posts.parts.ViewType;
import com.akarbowy.tagop.ui.posts.parts.embed.EmbedsPart;

public class CommentImageEmbedPart implements SinglePartDefinition<Comment, CommentImageEmbedView> {
    @Override public int getViewType() {
        return ViewType.EMBED_IMAGE;
    }

    @Override public Binder<CommentImageEmbedView> createBinder(Comment model) {
        return new CommentImageEmbedBinder(model);
    }

    @Override public boolean isNeeded(Comment model) {
        return model.getEmbed().type.equals(EmbedsPart.IMAGE);
    }
}
