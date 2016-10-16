package com.akarbowy.tagop.ui.posts.parts.comments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.akarbowy.partdefiner.Binder;
import com.akarbowy.partdefiner.PartManager;
import com.akarbowy.tagop.data.network.model.Comment;
import com.akarbowy.tagop.ui.posts.parts.ViewType;
import com.akarbowy.tagop.ui.posts.parts.comments.content.CommentContentPart;
import com.akarbowy.tagop.ui.posts.parts.comments.content.CommentContentView;
import com.akarbowy.tagop.ui.posts.parts.comments.embed.CommentEmbedsPart;
import com.akarbowy.tagop.ui.posts.parts.comments.embed.CommentImageEmbedView;
import com.akarbowy.tagop.ui.posts.parts.comments.embed.CommentVideoEmbedView;
import com.akarbowy.tagop.ui.posts.parts.comments.footer.CommentFooterPart;
import com.akarbowy.tagop.ui.posts.parts.comments.footer.CommentFooterView;

import java.util.ArrayList;

class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.PartHolder> {

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
    public CommentsAdapter.PartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.COMMENT_CONTENT:
                return new CommentsAdapter.PartHolder(new CommentContentView(parent.getContext()));
            case ViewType.COMMENT_FOOTER:
                return new CommentsAdapter.PartHolder(new CommentFooterView(parent.getContext()));
            case ViewType.EMBED_IMAGE:
                return new CommentsAdapter.PartHolder(new CommentImageEmbedView(parent.getContext()));
            case ViewType.EMBED_VIDEO:
                return new CommentsAdapter.PartHolder(new CommentVideoEmbedView(parent.getContext()));
            default:
                throw new RuntimeException("Unsupported view type.");
        }
    }

    @Override public void onBindViewHolder(CommentsAdapter.PartHolder holder, int position) {
        Binder<View> binder = partManager.getBinder(position);
        binder.prepare(holder.partView);
        binder.bind(holder.partView);
    }

    @Override
    public int getItemCount() {
        return partManager.getItemCount();
    }

    public void setItems(ArrayList<Comment> items) {
        partManager.setItemsForBinding(items);
        notifyDataSetChanged();
    }

    static class PartHolder extends RecyclerView.ViewHolder {
        View partView;

        PartHolder(View view) {
            super(view);
            this.partView = view;
        }
    }
}