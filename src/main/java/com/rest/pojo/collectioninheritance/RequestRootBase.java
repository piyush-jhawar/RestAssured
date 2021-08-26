package com.rest.pojo.collectioninheritance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRootBase {
    private String name;

    public RequestRootBase() {
    }

    public RequestRootBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
