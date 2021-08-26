package com.rest.pojo.collectioninheritance;

import java.util.List;

public class CollectionResponse extends CollectionBase {
    public List<FolderResponse> item;

    public CollectionResponse() {
    }

    public CollectionResponse(List<FolderResponse> item) {
        this.item = item;
    }

    public CollectionResponse(Info info, List<FolderResponse> item) {
        super(info);
        this.item = item;
    }

    public List<FolderResponse> getItem() {
        return item;
    }

    public void setItem(List<FolderResponse> item) {
        this.item = item;
    }
}
