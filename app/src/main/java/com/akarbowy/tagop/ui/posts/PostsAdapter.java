package com.akarbowy.tagop.ui.posts;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akarbowy.tagop.R;
import com.akarbowy.tagop.network.model.TagEntry;
import com.akarbowy.tagop.parto.PartManager;
import com.akarbowy.tagop.ui.posts.parts.ViewType;
import com.akarbowy.tagop.ui.posts.parts.counters.CountersPart;
import com.akarbowy.tagop.ui.posts.parts.counters.CountersView;
import com.akarbowy.tagop.ui.posts.parts.embed.EmbedsPart;
import com.akarbowy.tagop.ui.posts.parts.embed.ImageEmbedView;
import com.akarbowy.tagop.ui.posts.parts.embed.VideoEmbedView;
import com.akarbowy.tagop.ui.posts.parts.header.HeaderPart;
import com.akarbowy.tagop.ui.posts.parts.header.HeaderView;
import com.akarbowy.tagop.ui.posts.parts.other.SeparatorPart;
import com.akarbowy.tagop.ui.posts.parts.other.SeparatorView;
import com.akarbowy.tagop.ui.posts.parts.text.TextSectionPart;
import com.akarbowy.tagop.ui.posts.parts.text.TextSectionView;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_LOADER_VIEW_TYPE = 1000;

    private PartManager<TagEntry> partManager;
    private boolean loaderInserted;

    public PostsAdapter() {
        partManager = new PartManager<>();
        partManager.add(new SeparatorPart());
        partManager.add(new HeaderPart());
        partManager.add(new TextSectionPart());
        partManager.add(new EmbedsPart());
        partManager.add(new CountersPart());
        partManager.add(new SeparatorPart());
    }

    /**
     * It can vary from post to post coz some parts might be expanded.
     */
    public int getPostsBasicPartsCount() {
        return partManager.getBasicGroupPartsCount();
    }

    public void setItems(ArrayList<TagEntry> entries, boolean firstPage) {
        if (!entries.isEmpty()) {
            if (firstPage) {
                partManager.setBinders(entries);
            } else {
                partManager.appendBinders(entries);
            }
        }

        notifyDataSetChanged();
    }

    public void insertPageLoader() {
        loaderInserted = true;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override public void run() {
                notifyItemInserted(partManager.getItemCount());
            }
        });
    }

    public void removePageLoader() {
        if (loaderInserted) {
            loaderInserted = false;
            Handler h = new Handler();
            h.post(new Runnable() {
                @Override public void run() {
                    notifyItemRemoved(partManager.getItemCount());
                }
            });
        }
    }

    @Override public int getItemViewType(int position) {
        if (loaderInserted && position == partManager.getItemCount()) {
            return ITEM_LOADER_VIEW_TYPE;
        }
        return partManager.getItemViewType(position);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ViewType.SEPARATOR:
                return new PartManager.PartHolder(new SeparatorView(parent.getContext()));
            case ViewType.HEADER:
                return new PartManager.PartHolder(new HeaderView(parent.getContext()));
            case ViewType.TEXT_SECTION:
                return new PartManager.PartHolder(new TextSectionView(parent.getContext()));
            case ViewType.EMBED_IMAGE:
                return new PartManager.PartHolder(new ImageEmbedView(parent.getContext()));
            case ViewType.EMBED_VIDEO:
                return new PartManager.PartHolder(new VideoEmbedView(parent.getContext()));
            case ViewType.COUNTERS:
                return new PartManager.PartHolder(new CountersView(parent.getContext()));
            case ITEM_LOADER_VIEW_TYPE:
                View bar = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_loader, parent, false);
                return new LoaderHolder(bar);
            default:
                throw new RuntimeException("Unsupported view type.");
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PartManager.PartHolder) {
            partManager.onBindPartHolder((PartManager.PartHolder) holder, position);
        }
    }

    @Override public int getItemCount() {
        int itemCount = partManager.getItemCount();
        return loaderInserted ? itemCount + 1 : itemCount;
    }

    private static class LoaderHolder extends RecyclerView.ViewHolder {
        LoaderHolder(View itemView) {
            super(itemView);
        }
    }
}