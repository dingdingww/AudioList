package com.example.weiwang.audiodemo;

public class MediaEntity {

    public final static int STATUS_PLARY=1;
    public final static int STATUS_PAUSE=0;
    public final static int STATUS_END=2;

    String uri;
    String startTime;
    String endTime;
    boolean playStatus;


    public MediaEntity(String uri, String startTime, String endTime, boolean playStatus) {
        this.uri = uri;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playStatus = playStatus;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(boolean playStatus) {
        this.playStatus = playStatus;
    }
}
