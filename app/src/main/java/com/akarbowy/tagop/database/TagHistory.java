package com.akarbowy.tagop.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tagop")
public class TagHistory {

    @DatabaseField(generatedId = true) Long id;

    @DatabaseField private String tagName;

    public TagHistory() {
    }

    public TagHistory(String tag) {
        this.tagName = tag;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return tagName;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagHistory that = (TagHistory) o;

        return tagName.equals(that.tagName);

    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + tagName.hashCode();
        return result;
    }

    @Override public String toString() {
        return String.format("%s-%s", id, tagName);
    }
}
