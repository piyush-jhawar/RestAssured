package com.rest.pojo.collectioninheritance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRootRequest extends RequestRootBase {
    private RequestRequest request;

    public RequestRootRequest() {
    }

    public RequestRootRequest(RequestRequest request) {
        this.request = request;
    }

    public RequestRootRequest(String name, RequestRequest request) {
        super(name);
        this.request = request;
    }

    public RequestRequest getRequest() {
        return request;
    }

    public void setRequest(RequestRequest request) {
        this.request = request;
    }
}
