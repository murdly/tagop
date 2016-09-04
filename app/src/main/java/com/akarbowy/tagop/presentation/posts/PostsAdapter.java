package com.akarbowy.tagop.presentation.posts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.akarbowy.tagop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ItemViewHolder> {

    private List<String> tagNames;
    private AdapterView.OnItemClickListener onItemClickListener;

    public PostsAdapter(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ItemViewHolder(view, PostsAdapter.this);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String tagName = tagNames.get(position);
        holder.tagNameText.setText(tagName);
    }

    @Override
    public int getItemCount() {
        return tagNames.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void refresh(ArrayList<String> tags) {
        tagNames = tags;
        notifyDataSetChanged();
    }

    private void onItemHolderClick(ItemViewHolder itemHolder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_tag_name) TextView tagNameText;

        PostsAdapter adapter;

        public ItemViewHolder(View itemView, PostsAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        @Override public void onClick(View view) {
            adapter.onItemHolderClick(this);
        }
    }
}
