package com.rest.pojo.collection;

import java.util.List;

public class Collection {
    public Info info;
    public List<Folder> item;

    public Collection() {
    }

    public Collection(Info info, List<Folder> item) {
        this.info = info;
        this.item = item;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Folder> getItem() {
        return item;
    }

    public void setItem(List<Folder> item) {
        this.item = item;
    }
}
