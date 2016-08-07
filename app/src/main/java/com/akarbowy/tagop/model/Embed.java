package com.akarbowy.tagop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akarbowy on 07.08.16.
 */
public class Embed {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("preview")
    @Expose
    public String preview;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("plus18")
    @Expose
    public Boolean plus18;
    @SerializedName("source")
    @Expose
    public String source;
}
