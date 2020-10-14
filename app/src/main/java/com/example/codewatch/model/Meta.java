package com.example.codewatch.model;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("limit")
    private Integer limit;
    @SerializedName("next")
    private Integer next;
    @SerializedName("offset")
    private Integer offset;
    @SerializedName("previous")
    private Integer previous;
    @SerializedName("total_count")
    private Integer totalCount;

    public Meta(Integer limit, Integer next, Integer offset, Integer previous, Integer totalCount) {
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.previous = previous;
        this.totalCount = totalCount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPrevious() {
        return previous;
    }

    public void setPrevious(Integer previous) {
        this.previous = previous;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
