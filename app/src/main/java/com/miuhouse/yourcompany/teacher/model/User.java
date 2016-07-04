package com.miuhouse.yourcompany.teacher.model;

import java.io.Serializable;

/**
 * Created by kings on 7/1/2016.
 */
public class User implements Serializable {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
