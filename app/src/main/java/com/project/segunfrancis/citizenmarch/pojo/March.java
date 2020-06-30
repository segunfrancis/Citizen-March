package com.project.segunfrancis.citizenmarch.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SegunFrancis
 */
public class March implements Serializable {
    private String marchId = null;
    private String title;
    private String createdBy;
    private String date;
    private String time;
    private String location;
    private String description;
    private List<User> attendees = null;
    private String hashTags;
    private String marchPhotoUrl;

    public String getMarchId() {
        return marchId;
    }

    public void setMarchId(String marchId) {
        this.marchId = marchId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }

    public String getMarchPhotoUrl() {
        return marchPhotoUrl;
    }

    public void setMarchPhotoUrl(String marchPhotoUrl) {
        this.marchPhotoUrl = marchPhotoUrl;
    }
}
