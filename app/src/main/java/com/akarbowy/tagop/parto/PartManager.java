package com.akarbowy.tagop.parto;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class PartManager<M> {

    private BindersGenerator<M, View> generator;
    private List<PartDefinition<M>> basicGroupPartDefinition;
    private List<Pair<SinglePartDefinition<M, View>, Binder<View>>> binders;

    public PartManager() {
        basicGroupPartDefinition = new ArrayList<>();
        binders = new ArrayList<>();
        generator = new BindersGenerator<>();
    }

    public void add(PartDefinition<M> definition) {
        basicGroupPartDefinition.add(definition);
    }

    public int getItemViewType(int position) {
        return binders.get(position).first.getViewType();
    }

    public int getItemCount() {
        return binders.size();
    }

    public void onBindPartHolder(PartHolder holder, int position) {
        Binder<View> binder = binders.get(position).second;
        binder.prepare(holder.partView);
        binder.bind(holder.partView);
    }

    public void appendBinders(List<M> items) {
        binders.addAll(generator.generateBinders(items, basicGroupPartDefinition));
    }

    public void setBinders(List<M> items) {
        binders.clear();
        binders.addAll(generator.generateBinders(items, basicGroupPartDefinition));
    }

    public int getBasicGroupPartsCount(){
        return basicGroupPartDefinition.size();
    }

    public static class PartHolder extends RecyclerView.ViewHolder {
        View partView;

        public PartHolder(View view) {
            super(view);
            this.partView = view;
        }
    }
}
