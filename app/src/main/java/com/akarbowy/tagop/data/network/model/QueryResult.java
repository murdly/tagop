package com.akarbowy.tagop.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryResult {
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("items")
    @Expose
    public List<Post> entries;

}
