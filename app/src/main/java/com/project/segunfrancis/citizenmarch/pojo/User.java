package com.project.segunfrancis.citizenmarch.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SegunFrancis
 */
public class User implements Serializable {
    private String userId;
    private String name;
    private String profilePhotoUrl;
    private List<String> attending;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public List<String> getAttending() {
        return attending;
    }

    public void setAttending(List<String> attending) {
        this.attending = attending;
    }
}
