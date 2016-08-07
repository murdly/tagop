package com.akarbowy.tagop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by akarbowy on 07.08.16.
 */
public class QueryResult {
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("items")
    @Expose
    public List<TagEntry> entries;

}
