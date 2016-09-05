package com.akarbowy.tagop.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akarbowy.tagop.R;

/**
 * source: http://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/
 * extended to support empty state view
 */
public class RecyclerSupport {

    private final RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private View emptyStateView;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                return onItemLongClickListener.onItemLongClicked(recyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.AdapterDataObserver emptyStateObserver = new RecyclerView.AdapterDataObserver() {
        @Override public void onChanged() {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && emptyStateView != null) {
                boolean hasItems = adapter.getItemCount() != 0;
                emptyStateView.setVisibility(hasItems ? View.GONE : View.VISIBLE);
                recyclerView.setVisibility(hasItems ? View.VISIBLE : View.GONE);
            }

        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (onItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (onItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private RecyclerSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setTag(R.id.item_click_support, this);
        this.recyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static RecyclerSupport addTo(RecyclerView view) {
        RecyclerSupport support = (RecyclerSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new RecyclerSupport(view);
        }
        return support;
    }

    public static RecyclerSupport removeFrom(RecyclerView view) {
        RecyclerSupport support = (RecyclerSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RecyclerSupport setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    public RecyclerSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
        return this;
    }

    public RecyclerSupport setEmptyStateView(View view) {
        emptyStateView = view;
        recyclerView.getAdapter().registerAdapterDataObserver(emptyStateObserver);
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
