package com.akarbowy.tagop.utils;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StateSwitcher {

    private ArrayList<Pair<View, Integer>> pairs;
    private int currentState = -1;

    public void setViews(List<View> views, int[] stateIds){
        if(views.size() != stateIds.length){
            throw new RuntimeException("Must be a valid list of view,id pairs");
        }
        pairs = new ArrayList<>();
        for (int i = 0; i < views.size(); i++) {
            Pair<View, Integer> view = new Pair<>(views.get(i), stateIds[i]);
            pairs.add(view);
        }

    }

    /**
     * If no view associated with stateId,
     * visibility of all views is set to GONE.
     */
    public void setState(int stateId){
        if(currentState == stateId){
            return;
        }

        for (Pair<View, Integer> view : pairs) {
            view.first.setVisibility(view.second == stateId ? View.VISIBLE : View.GONE);
        }

        currentState = stateId;
    }
}
