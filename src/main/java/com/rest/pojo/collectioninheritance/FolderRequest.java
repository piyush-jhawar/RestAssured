package com.rest.pojo.collectioninheritance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.pojo.collection.RequestRoot;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)

public class FolderRequest extends FolderBase {
    private List<RequestRootRequest> item;

    public FolderRequest() {
    }

    public FolderRequest(List<RequestRootRequest> item) {
        this.item = item;
    }

    public FolderRequest(String name, List<RequestRootRequest> item) {
        super(name);
        this.item = item;
    }

    public List<RequestRootRequest> getItem() {
        return item;
    }

    public void setItem(List<RequestRootRequest> item) {
        this.item = item;
    }
}
