package com.rest.pojo.collectioninheritance;

import java.util.List;

public abstract class CollectionBase {
    public Info info;

    public CollectionBase() {
    }

    public CollectionBase(Info info) {
        this.info = info;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
