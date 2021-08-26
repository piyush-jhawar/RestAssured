package com.rest.pojo.collectioninheritance;

import java.util.List;

public class RequestResponse extends RequestBase {
    private URL url;

    public RequestResponse() {
    }

    public RequestResponse(URL url) {
        this.url = url;
    }

    public RequestResponse(String method, List<Header> header, Body body, String description, URL url) {
        super(method, header, body, description);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
