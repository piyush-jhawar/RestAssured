package com.rest.pojo.collectioninheritance;

import java.util.List;

public class CollectionRequest extends CollectionBase {
    public List<FolderRequest> item;

    public CollectionRequest() {
    }

    public CollectionRequest(List<FolderRequest> item) {
        this.item = item;
    }

    public CollectionRequest(Info info, List<FolderRequest> item) {
        super(info);
        this.item = item;
    }

    public List<FolderRequest> getItem() {
        return item;
    }

    public void setItem(List<FolderRequest> item) {
        this.item = item;
    }
}
