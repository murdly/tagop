package com.akarbowy.tagop.parto;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PartManager<M> {

    private BindersGenerator<M> generator;
    private List<PartDefinition> absoluteParts;
    private ArrayList<Pair<PartDefinition, Binder>> binders;

    public PartManager() {
        absoluteParts = new ArrayList<>();
        binders = new ArrayList<>();
        generator = new BindersGenerator<>();
    }

    public void add(PartDefinition definition) {
        absoluteParts.add(definition);
    }

    public int getItemViewType(int position) {
        return binders.get(position).first.getViewType();
    }

    public int getItemCount() {
        return binders.size();
    }

    public void onBindPartHolder(PartHolder holder, int position) {
        Binder binder = binders.get(position).second;
        binder.prepare(holder.partView);
        binder.bind(holder.partView);
    }

    public void appendBinders(ArrayList<M> items) {
        binders.addAll(generator.generateBinders(absoluteParts, items));
    }

    public void setBinders(ArrayList<M> items) {
        binders.clear();
        binders.addAll(generator.generateBinders(absoluteParts, items));
    }

    public int getPartsCount(){
        return absoluteParts.size();
    }

    public static class PartHolder extends RecyclerView.ViewHolder {
        public View partView;

        public PartHolder(View view) {
            super(view);
            this.partView = view;
        }
    }
}
