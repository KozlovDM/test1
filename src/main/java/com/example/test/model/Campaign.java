package com.example.test.model;

import java.util.List;
import java.util.UUID;

public class Campaign {
    private UUID id;
    private String name;
    private Status status;
    private List<Scenario> scenarios;

    public Campaign() {
        this.status = Status.NEW;
    }

    public Campaign(String name, Status status, List<Scenario> scenarios) {
        this.name = name;
        this.status = status;
        this.scenarios = scenarios;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", scenarios=" + scenarios +
                '}';
    }
}
