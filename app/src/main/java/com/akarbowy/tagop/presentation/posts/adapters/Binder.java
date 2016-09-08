package com.akarbowy.tagop.presentation.posts.adapters;

public interface Binder<V> {

    void prepare(V view);

    void bind(V view);

}
