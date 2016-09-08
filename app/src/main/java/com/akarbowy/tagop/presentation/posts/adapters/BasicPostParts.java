package com.akarbowy.tagop.presentation.posts.adapters;

import android.util.Pair;

import com.akarbowy.tagop.model.TagEntry;
import com.akarbowy.tagop.presentation.posts.adapters.header.HeaderPart;
import com.akarbowy.tagop.presentation.posts.adapters.other.SeparatorPart;
import com.akarbowy.tagop.presentation.posts.adapters.text.TextSectionPart;

import java.util.ArrayList;
import java.util.List;

public class BasicPostParts {
    private List<PartDefinition> parts;

    public BasicPostParts() {
        parts = new ArrayList<>();

        parts.add(new SeparatorPart());
        parts.add(new HeaderPart());
        parts.add(new TextSectionPart());
        parts.add(new SeparatorPart());
    }

    public ArrayList<Pair<PartDefinition, Binder>> generateBinders(TagEntry entry){
        ArrayList<Pair<PartDefinition, Binder>> binders = new ArrayList<>();
        for (PartDefinition part : parts) {
            if(part.isNeeded(entry)){
                Pair pair = new Pair(part, part.createBinder(entry));
                binders.add(pair);
            }
        }

        return binders;
    }

}
