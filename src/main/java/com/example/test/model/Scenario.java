package com.example.test.model;

import java.util.Date;
import java.util.UUID;

public class Scenario {
    private UUID id;
    private String name;
    private Date startDate;
    private Date endDate;
    private UUID campaignId;

    public Scenario() {
    }

    public Scenario(String name, Date startDate, Date endDate, UUID campaignId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignId = campaignId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UUID getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public String toString() {
        return "Scenarios{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
