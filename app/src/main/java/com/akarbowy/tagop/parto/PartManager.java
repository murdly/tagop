package com.akarbowy.tagop.parto;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PartManager<M> {

    private BindersGenerator<M> generator;
    private List<PartDefinition> basicParts;
    private ArrayList<Pair<PartDefinition, Binder>> binders;

    public PartManager() {
        basicParts = new ArrayList<>();
        binders = new ArrayList<>();
        generator = new BindersGenerator<>();
    }

    public void add(PartDefinition definition) {
        basicParts.add(definition);
    }

    public int getItemViewType(int position) {
        return binders.get(position).first.getViewType();
    }

    public int getItemCount() {
        return binders.size();
    }

    public void onBindViewHolder(PartHolder holder, int position) {
        Binder binder = binders.get(position).second;
        binder.bind(holder.partView);
    }

    public void setBinders(ArrayList<M> items) {
        binders = generator.generateBinders(basicParts, items);
    }

    public static class PartHolder extends RecyclerView.ViewHolder {
        public View partView;

        public PartHolder(View view) {
            super(view);
            this.partView = view;
        }
    }
}
