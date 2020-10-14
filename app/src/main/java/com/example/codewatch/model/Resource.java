package com.example.codewatch.model;

import com.google.gson.annotations.SerializedName;

public class Resource {

    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;

    public Resource(String icon, Integer id, String name) {
        this.icon = icon;
        this.id = id;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
