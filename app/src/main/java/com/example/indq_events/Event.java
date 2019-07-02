package com.example.indq_events;

import com.google.android.gms.maps.internal.IMapFragmentDelegate;

public class Event {
    private String title;
    private String image;
    private String date;
    private String attendances;
    private String description;
    public Event(){}
    public Event(String title,String date,String attendances, String description,String image){
        this.title = title;
        this.date = date;
        this.attendances =attendances;
        this.description = description;
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAttendances() {
        return attendances;
    }
    public void setAttendances(String attendances) {
        this.attendances = attendances;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
