package com.rest.pojo.collectioninheritance;

public class CollectionRootResponse extends CollectionRootBase {
    public CollectionResponse collection;

    public CollectionRootResponse() {
    }

    public CollectionRootResponse(CollectionResponse collection) {
        this.collection = collection;
    }

    public CollectionResponse getCollection() {
        return collection;
    }

    public void setCollection(CollectionResponse collection) {
        this.collection = collection;
    }
}
