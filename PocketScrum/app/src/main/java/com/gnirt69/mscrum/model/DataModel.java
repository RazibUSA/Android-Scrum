package com.gnirt69.mscrum.model;


import java.io.Serializable;

public class DataModel implements Serializable {
    private static final long serialVersionUID = 1L;

    long version;
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }
}
