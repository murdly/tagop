package com.akarbowy.tagop.ui.posts.parts.comments;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.PartManager;
import com.akarbowy.tagop.ui.posts.parts.ViewType;
import com.akarbowy.tagop.ui.posts.parts.comments.content.CommentContentPart;
import com.akarbowy.tagop.ui.posts.parts.comments.content.CommentContentView;
import com.akarbowy.tagop.ui.posts.parts.comments.embed.CommentEmbedsPart;
import com.akarbowy.tagop.ui.posts.parts.comments.embed.CommentImageEmbedView;
import com.akarbowy.tagop.ui.posts.parts.comments.embed.CommentVideoEmbedView;
import com.akarbowy.tagop.ui.posts.parts.comments.footer.CommentFooterPart;
import com.akarbowy.tagop.ui.posts.parts.comments.footer.CommentFooterView;

import java.util.ArrayList;

class CommentsAdapter extends RecyclerView.Adapter<PartManager.PartHolder> {

    private PartManager<Comment> partManager;

    public CommentsAdapter() {
        partManager = new PartManager<>();
        partManager.add(new CommentContentPart());
        partManager.add(new CommentEmbedsPart());
        partManager.add(new CommentFooterPart());
    }

    @Override public int getItemViewType(int position) {
        return partManager.getItemViewType(position);
    }

    @Override
    public PartManager.PartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.COMMENT_CONTENT:
                return new PartManager.PartHolder(new CommentContentView(parent.getContext()));
            case ViewType.COMMENT_FOOTER:
                return new PartManager.PartHolder(new CommentFooterView(parent.getContext()));
            case ViewType.EMBED_IMAGE:
                return new PartManager.PartHolder(new CommentImageEmbedView(parent.getContext()));
            case ViewType.EMBED_VIDEO:
                return new PartManager.PartHolder(new CommentVideoEmbedView(parent.getContext()));
            default:
                throw new RuntimeException("Unsupported view type.");
        }
    }

    @Override public void onBindViewHolder(PartManager.PartHolder holder, int position) {
        partManager.onBindPartHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return partManager.getItemCount();
    }

    public void setItems(ArrayList<Comment> items) {
        partManager.setBinders(items);
        notifyDataSetChanged();
    }
}