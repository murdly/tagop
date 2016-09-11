package com.akarbowy.tagop.parto;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BindersGenerator<M> {

    public final ArrayList<Pair<PartDefinition, Binder>> generateBinders(List<PartDefinition> basicParts, ArrayList<M> items) {
        ArrayList<Pair<PartDefinition, Binder>> binders = new ArrayList<>();
        for (M item : items) {
            binders.addAll(generate(basicParts, item));
        }

        return binders;
    }


    private ArrayList<Pair<PartDefinition, Binder>> generate(List<PartDefinition> basicParts, M item) {
        ArrayList<Pair<PartDefinition, Binder>> binders = new ArrayList<>();
        for (PartDefinition part : basicParts) {
            if (part.isNeeded(item)) {
                Pair<PartDefinition, Binder> pair = new Pair<>(part, part.createBinder(item));
                binders.add(pair);
            }
        }

        return binders;
    }
}
