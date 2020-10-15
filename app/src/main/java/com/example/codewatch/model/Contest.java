package com.example.codewatch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Contest implements Parcelable {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("objects")
    private ArrayList<Objects> objects;

    public Contest(Meta meta, ArrayList<Objects> objects) {
        this.meta = meta;
        this.objects = objects;
    }

    protected Contest(Parcel in) {
        meta = in.readParcelable(Meta.class.getClassLoader());
        objects = in.createTypedArrayList(Objects.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(meta, flags);
        dest.writeTypedList(objects);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contest> CREATOR = new Creator<Contest>() {
        @Override
        public Contest createFromParcel(Parcel in) {
            return new Contest(in);
        }

        @Override
        public Contest[] newArray(int size) {
            return new Contest[size];
        }
    };

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
