package com.example.codewatch.model;

import com.google.gson.annotations.SerializedName;

public class Objects {

    @SerializedName("duration")
    private Integer duration;
    @SerializedName("end")
    private String end;
    @SerializedName("event")
    private String event;
    @SerializedName("href")
    private String href;
    @SerializedName("id")
    private Integer id;
    @SerializedName("resource")
    private Resource resource;
    @SerializedName("start")
    private String start;

    public Objects(Integer duration, String end, String event, String href, Integer id, Resource resource, String start) {
        this.duration = duration;
        this.end = end;
        this.event = event;
        this.href = href;
        this.id = id;
        this.resource = resource;
        this.start = start;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
