package com.diginals.microlancer;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by jessica on 2016-09-25.
 */
@IgnoreExtraProperties
public class Job {
    public String postedDate;
    public String lastModified;
    public String jobTitle;
    public String description;
    public String paymentType;
    public String wageType;
    public String workDate;
    public String location;
    public User owner;
    public java.util.Map<String, Boolean> jobb = new HashMap<>();


    public Job() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Job(String postedDate, String lastModified, String jobTitle, String description, String paymentType, String wageType, String workDate, String location, User owner) {
        this.postedDate = postedDate;
        this.lastModified = lastModified;
        this.jobTitle = jobTitle;
        this.description = description;
        this.paymentType = paymentType;
        this.wageType = wageType;
        this.workDate = workDate;
        this.location = location;
        this.owner = owner;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getWageType() {
        return wageType;
    }

    public void setWageType(String wageType) {
        this.wageType = wageType;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // [START post_to_map]
    @Exclude
    public java.util.Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("postedDate", postedDate);
        result.put("lastModified", lastModified);
        result.put("jobTitle", jobTitle);
        result.put("description", description);
        result.put("paymentType", paymentType);
        result.put("wageType", wageType);
        result.put("workDate", workDate);
        result.put("location", location);
        result.put("owner", owner);
        result.put("jobb", jobb);
        return result;
    }
}
