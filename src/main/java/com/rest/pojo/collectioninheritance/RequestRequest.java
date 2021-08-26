package com.rest.pojo.collectioninheritance;

import java.util.List;

public class RequestRequest extends RequestBase {
    private String url;

    public RequestRequest() {
    }

    public RequestRequest(String url) {
        this.url = url;
    }

    public RequestRequest(String method, List<Header> header, Body body, String description, String url) {
        super(method, header, body, description);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
