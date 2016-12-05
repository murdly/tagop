package com.akarbowy.tagop.ui.search;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.data.database.model.TagModel;

import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ItemViewHolder> {

    private final Comparator<TagModel> comparator;

    private final SortedList<TagModel> items = new SortedList<>(TagModel.class, new SortedList.Callback<TagModel>() {
        @Override
        public int compare(TagModel a, TagModel b) {
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
        public boolean areContentsTheSame(TagModel oldItem, TagModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(TagModel item1, TagModel item2) {
            return item1.equals(item2);
        }
    });

    public SearchHistoryAdapter(Comparator<TagModel> comparator) {
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
        TagModel tag = items.get(position);
        holder.tagNameText.setText(tag.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceAll(List<TagModel> models) {
        items.beginBatchedUpdates();
        for (int i = items.size() - 1; i >= 0; i--) {
            final TagModel model = items.get(i);
            if (!models.contains(model)) {
                items.remove(model);
            }
        }
        items.addAll(models);
        items.endBatchedUpdates();
    }

    public TagModel getItem(int position) {
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
