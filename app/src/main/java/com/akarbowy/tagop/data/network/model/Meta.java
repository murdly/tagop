package com.akarbowy.tagop.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("tag")
    @Expose
    public String tag;
    @SerializedName("counters")
    @Expose
    public Counters counters;
}
