package com.example.codewatch.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Meta implements Parcelable {

    @SerializedName("limit")
    private Integer limit;
    @SerializedName("next")
    private String next;
    @SerializedName("offset")
    private Integer offset;
    @SerializedName("previous")
    private String previous;
    @SerializedName("total_count")
    private Integer totalCount;

    public Meta(Integer limit, String next, Integer offset, String previous, Integer totalCount) {
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.previous = previous;
        this.totalCount = totalCount;
    }

    protected Meta(Parcel in) {
        if (in.readByte() == 0) {
            limit = null;
        } else {
            limit = in.readInt();
        }
        next = in.readString();
        if (in.readByte() == 0) {
            offset = null;
        } else {
            offset = in.readInt();
        }
        previous = in.readString();
        if (in.readByte() == 0) {
            totalCount = null;
        } else {
            totalCount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (limit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(limit);
        }
        dest.writeString(next);
        if (offset == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(offset);
        }
        dest.writeString(previous);
        if (totalCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalCount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
