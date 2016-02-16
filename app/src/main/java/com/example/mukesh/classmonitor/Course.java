package com.example.mukesh.classmonitor;

/**
 * Created by mukesh on 2/13/2016.
 */
public class Course {


    private String courseTitle;
    private double latitude;
    private double longitude;
    private long timestamp;
    private String date ;
    private String time ;
    public Course() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate(){ return date ;}

    public void setDate(String date){ this.date = date ; }

    public void setTime(String time){this.time = time; }

    public String getTime(){ return time; }


}
