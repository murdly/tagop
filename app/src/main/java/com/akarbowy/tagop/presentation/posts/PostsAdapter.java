package com.akarbowy.tagop.presentation.posts;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.akarbowy.tagop.presentation.posts.adapters.Binder;
import com.akarbowy.tagop.presentation.posts.adapters.PartDefinition;
import com.akarbowy.tagop.presentation.posts.adapters.header.HeaderView;
import com.akarbowy.tagop.presentation.posts.adapters.other.SeparatorView;
import com.akarbowy.tagop.presentation.posts.adapters.text.TextSectionView;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PartHolder> {

    private ArrayList<Pair<PartDefinition, Binder>> partsWithBinders;

    public PostsAdapter() {
        partsWithBinders = new ArrayList<>();
    }

    @Override public int getItemViewType(int position) {
        return partsWithBinders.get(position).first.getViewType().getId();
    }

    @Override
    public PartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
//            case ViewType.HEADER.getId():getId
            case 1:
                return new PartHolder(new HeaderView(parent.getContext()));
            case 2:
                return new PartHolder(new TextSectionView(parent.getContext()));
            case 4:
                return new PartHolder(new SeparatorView(parent.getContext()));
            default:
                return new PartHolder(new HeaderView(parent.getContext()));
        }
    }

    @Override public void onBindViewHolder(PartHolder holder, int position) {
        Binder binder = partsWithBinders.get(position).second;
        binder.bind(holder.partView);
    }

    @Override
    public int getItemCount() {
        return partsWithBinders.size();
    }

    public void setPartsWithBinders(ArrayList<Pair<PartDefinition, Binder>> partsWithBinders) {
        this.partsWithBinders = partsWithBinders;
    }

    public class PartHolder extends RecyclerView.ViewHolder {
        public View partView;

        public PartHolder(View view) {
            super(view);
            this.partView = view;
        }
    }
}
