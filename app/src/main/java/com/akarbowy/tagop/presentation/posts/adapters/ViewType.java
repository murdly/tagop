package com.akarbowy.tagop.presentation.posts.adapters;

public enum ViewType {
    HEADER(1), TEXT_SECTION(2), TAGS(3), SEPARATOR(4);

    private final int id;

    ViewType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
