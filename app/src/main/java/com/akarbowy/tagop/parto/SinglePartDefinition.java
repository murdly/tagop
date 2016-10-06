package com.akarbowy.tagop.parto;

public interface SinglePartDefinition<M, V> extends PartDefinition<M>{

    int getViewType();

    Binder<V> createBinder(M model);

}
