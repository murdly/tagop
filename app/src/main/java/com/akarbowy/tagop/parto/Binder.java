package com.akarbowy.tagop.parto;

public interface Binder<V> {

    void prepare(V view);

    void bind(V view);

}
