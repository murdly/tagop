package com.akarbowy.tagop.ui.posts.parts.comments;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.akarbowy.tagop.network.model.Comment;
import com.akarbowy.tagop.parto.PartManager;
import com.akarbowy.tagop.ui.posts.parts.ViewType;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<PartManager.PartHolder> {

    private PartManager<Comment> partManager;

    public CommentsAdapter() {
        partManager = new PartManager<>();
        partManager.add(new CommentPart());
    }

    @Override public int getItemViewType(int position) {
        return partManager.getItemViewType(position);
    }

    @Override
    public PartManager.PartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.COMMENT:
                return new PartManager.PartHolder(new CommentView(parent.getContext()));
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