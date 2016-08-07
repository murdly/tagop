package com.akarbowy.tagop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akarbowy on 07.08.16.
 */
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
