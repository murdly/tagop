package com.akarbowy.tagop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akarbowy on 07.08.16.
 */
public class Meta {
    @SerializedName("tag")
    @Expose
    public String tag;
    @SerializedName("counters")
    @Expose
    public Counters counters;
}
