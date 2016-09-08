package com.akarbowy.tagop.presentation.posts.adapters;

public interface PartDefinition<M, V> {
    ViewType getViewType();

    Binder<V> createBinder(M viewObject);

    boolean isNeeded(M viewObject);
}
