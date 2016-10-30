package com.tripint.intersight.entity.discuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorUserAbility implements Serializable {

    private boolean timestamps;
    private boolean incrementing;
    private boolean exists;
    private boolean wasRecentlyCreated;

    public boolean isTimestamps() {
        return timestamps;
    }

    public void setTimestamps(boolean timestamps) {
        this.timestamps = timestamps;
    }

    public boolean isIncrementing() {
        return incrementing;
    }

    public void setIncrementing(boolean incrementing) {
        this.incrementing = incrementing;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isWasRecentlyCreated() {
        return wasRecentlyCreated;
    }

    public void setWasRecentlyCreated(boolean wasRecentlyCreated) {
        this.wasRecentlyCreated = wasRecentlyCreated;
    }
}
