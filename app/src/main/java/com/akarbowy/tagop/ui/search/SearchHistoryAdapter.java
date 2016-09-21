package com.akarbowy.tagop.ui.search;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.database.TagHistory;

import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ItemViewHolder> {

    private final Comparator<TagHistory> comparator;

    private final SortedList<TagHistory> items = new SortedList<>(TagHistory.class, new SortedList.Callback<TagHistory>() {
        @Override
        public int compare(TagHistory a, TagHistory b) {
            return comparator.compare(a, b);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(TagHistory oldItem, TagHistory newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(TagHistory item1, TagHistory item2) {
            return item1 == item2;
        }
    });

    public SearchHistoryAdapter(Comparator<TagHistory> comparator) {
        this.comparator = comparator;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        TagHistory tagHistory = items.get(position);
        holder.tagNameText.setText(tagHistory.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceAll(List<TagHistory> models) {
        items.beginBatchedUpdates();
        for (int i = items.size() - 1; i >= 0; i--) {
            final TagHistory model = items.get(i);
            if (!models.contains(model)) {
                items.remove(model);
            }
        }
        items.addAll(models);
        items.endBatchedUpdates();
    }

    public TagHistory getItem(int position) {
        return items.get(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_tag_name) TextView tagNameText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}