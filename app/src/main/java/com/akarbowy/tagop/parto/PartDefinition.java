package com.akarbowy.tagop.parto;

public interface PartDefinition<M, V> {
    int getViewType();

    Binder<V> createBinder(M viewObject);

    boolean isNeeded(M viewObject);
}
