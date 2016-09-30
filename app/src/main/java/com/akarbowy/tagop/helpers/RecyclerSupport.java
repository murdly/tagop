package com.akarbowy.tagop.helpers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akarbowy.tagop.R;

public class RecyclerSupport {

    private final RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnNextPageRequestListener onNextPageRequestListener;
    private View emptyStateView;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
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

    private RecyclerView.OnChildAttachStateChangeListener attachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (onItemClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
            if (onItemLongClickListener != null) {
                view.setOnLongClickListener(onLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
        }
    };

    private RecyclerSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setTag(R.id.recycler_support, this);
        this.recyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    public static RecyclerSupport addTo(RecyclerView view) {
        RecyclerSupport support = (RecyclerSupport) view.getTag(R.id.recycler_support);
        if (support == null) {
            support = new RecyclerSupport(view);
        }
        return support;
    }

    public static RecyclerSupport removeFrom(RecyclerView view) {
        RecyclerSupport support = (RecyclerSupport) view.getTag(R.id.recycler_support);
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
        emptyStateView.setVisibility(View.GONE);
        recyclerView.getAdapter().registerAdapterDataObserver(emptyStateObserver);
        return this;
    }

    public RecyclerSupport setOnNextPageRequestListener(OnNextPageRequestListener listener, int oneItemViewsCount, boolean showsLoader) {
        onNextPageRequestListener = listener;
        recyclerView.addOnScrollListener(new OnLoadMoreScrollListener(oneItemViewsCount, showsLoader));
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(attachListener);
        view.setTag(R.id.recycler_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnNextPageRequestListener {
        void onLoadNextPage();
    }

    private class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {
        private final int bufferThreshold;
        private final int loaderOffset;
        private int previousTotalItemCount = 0;
        private boolean isWaitingForDataToLoad = true;


        public OnLoadMoreScrollListener(int itemViewCount, boolean showesLoader) {
            bufferThreshold = 5 * itemViewCount;
            loaderOffset = showesLoader ? 1 : 0;
        }

        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            if (dy <= 0) {
                return;
            }

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else {
                throw new RuntimeException("Unsupported layout manager.");
            }

            int totalItemCount = layoutManager.getItemCount();
            if (totalItemCount < previousTotalItemCount) {
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.isWaitingForDataToLoad = true;
                }
            }

            if (isWaitingForDataToLoad && (totalItemCount - loaderOffset > previousTotalItemCount)) {
                isWaitingForDataToLoad = false;
                previousTotalItemCount = totalItemCount;
            }

            if (!isWaitingForDataToLoad && (lastVisibleItemPosition + bufferThreshold) > totalItemCount) {
                onNextPageRequestListener.onLoadNextPage();
                isWaitingForDataToLoad = true;
            }

        }
    }
}