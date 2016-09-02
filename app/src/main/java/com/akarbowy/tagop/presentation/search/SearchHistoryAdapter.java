package com.akarbowy.tagop.presentation.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akarbowy.tagop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ItemViewHolder> {

    private List<String> tagNames;

    public SearchHistoryAdapter(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ItemViewHolder(view);
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

    public void refresh(ArrayList<String> tags) {
        tagNames = tags;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_tag_name) TextView tagNameText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
