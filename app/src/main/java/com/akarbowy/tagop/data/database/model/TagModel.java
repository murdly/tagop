package com.akarbowy.tagop.data.database.model;

import java.util.UUID;

public class TagModel {

    private String id;

    private String title;

    private boolean saveInHistory = false;

    public TagModel(String title, boolean saveInHistory) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.saveInHistory = saveInHistory;
    }

    public TagModel(String id, String title) {
        this.id = id;
        this.title = title;
        this.saveInHistory = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isForSaving() {
        return saveInHistory;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagModel that = (TagModel) o;

        return title.equals(that.title);

    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override public String toString() {
        return String.format("%s", title);
    }

}
