package com.example.codewatch.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Contest {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("objects")
    private ArrayList<Objects> objects;

    public Contest(Meta meta, ArrayList<Objects> objects) {
        this.meta = meta;
        this.objects = objects;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<Objects> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Objects> objects) {
        this.objects = objects;
    }
}
