package com.example.codewatch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Objects implements Parcelable {

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

    protected Objects(Parcel in) {
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        end = in.readString();
        event = in.readString();
        href = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        start = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(end);
        dest.writeString(event);
        dest.writeString(href);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(start);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Objects> CREATOR = new Creator<Objects>() {
        @Override
        public Objects createFromParcel(Parcel in) {
            return new Objects(in);
        }

        @Override
        public Objects[] newArray(int size) {
            return new Objects[size];
        }
    };

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
