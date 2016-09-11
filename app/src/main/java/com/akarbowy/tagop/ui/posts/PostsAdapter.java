package com.akarbowy.tagop.ui.posts;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.PartManager;
import com.akarbowy.tagop.ui.posts.parts.ViewType;
import com.akarbowy.tagop.ui.posts.parts.counters.CountersPart;
import com.akarbowy.tagop.ui.posts.parts.counters.CountersView;
import com.akarbowy.tagop.ui.posts.parts.header.HeaderPart;
import com.akarbowy.tagop.ui.posts.parts.header.HeaderView;
import com.akarbowy.tagop.ui.posts.parts.other.SeparatorPart;
import com.akarbowy.tagop.ui.posts.parts.other.SeparatorView;
import com.akarbowy.tagop.ui.posts.parts.text.TextSectionPart;
import com.akarbowy.tagop.ui.posts.parts.text.TextSectionView;
import com.akarbowy.tagop.ui.posts.parts.embed.EmbedPart;
import com.akarbowy.tagop.ui.posts.parts.embed.EmbedView;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PartManager.PartHolder> {

    private PartManager<TagEntry> partManager;

    public PostsAdapter() {
        partManager = new PartManager<>();
        partManager.add(new SeparatorPart());
        partManager.add(new HeaderPart());
        partManager.add(new TextSectionPart());
        partManager.add(new EmbedPart());
        partManager.add(new CountersPart());
        partManager.add(new SeparatorPart());
    }

    @Override public int getItemViewType(int position) {
        return partManager.getItemViewType(position);
    }

    @Override
    public PartManager.PartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.SEPARATOR:
                return new PartManager.PartHolder(new SeparatorView(parent.getContext()));
            case ViewType.HEADER:
                return new PartManager.PartHolder(new HeaderView(parent.getContext()));
            case ViewType.TEXT_SECTION:
                return new PartManager.PartHolder(new TextSectionView(parent.getContext()));
            case ViewType.EMBED:
                return new PartManager.PartHolder(new EmbedView(parent.getContext()));
            case ViewType.COUNTERS:
                return new PartManager.PartHolder(new CountersView(parent.getContext()));
            default:
                throw new RuntimeException("Unsupported view type.");
        }
    }

    @Override public void onBindViewHolder(PartManager.PartHolder holder, int position) {
        partManager.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return partManager.getItemCount();
    }

    public void setItems(ArrayList<TagEntry> entries) {
        partManager.setBinders(entries);
        notifyDataSetChanged();
    }
}