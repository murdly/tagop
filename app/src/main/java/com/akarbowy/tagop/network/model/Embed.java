package com.akarbowy.tagop.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Embed {
    @SerializedName("type") @Expose public String type;
    @SerializedName("preview") @Expose public String previewUrl;
    @SerializedName("url") @Expose public String url;
    @SerializedName("plus18") @Expose public Boolean plus18;
    @SerializedName("source") @Expose public String source;

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getUrl() {
        return url;
    }
}
