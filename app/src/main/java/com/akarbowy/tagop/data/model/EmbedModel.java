package com.akarbowy.tagop.data.model;

import java.util.UUID;

public class EmbedModel {

    private String id;
    private String type;
    private String previewUrl;
    private String url;

    public EmbedModel() {
        this.id = UUID.randomUUID().toString();
    }

    public EmbedModel(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }
}
