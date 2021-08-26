package com.rest.pojo.collectioninheritance;

public class CollectionRootRequest extends CollectionRootBase {
    public CollectionRequest collection;

    public CollectionRootRequest() {
    }

    public CollectionRootRequest(CollectionRequest collection) {
        this.collection = collection;
    }

    public CollectionRequest getCollection() {
        return collection;
    }

    public void setCollection(CollectionRequest collection) {
        this.collection = collection;
    }
}
