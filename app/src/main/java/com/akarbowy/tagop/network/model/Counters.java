package com.akarbowy.tagop.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Counters {
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("entries")
    @Expose
    public Integer entries;
    @SerializedName("links")
    @Expose
    public Integer links;
}
