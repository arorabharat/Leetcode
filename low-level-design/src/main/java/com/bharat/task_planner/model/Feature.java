package com.bharat.task_planner.model;


import com.bharat.task_planner.constants.FeatureImpactStatus;

public class Feature extends Task {

    private String summary;
    private FeatureImpactStatus featureImpactStatus;

    public Feature(String title, User creator, TaskStatus status, long dueDate, String id) {
        super(title, creator, status, dueDate, id);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public FeatureImpactStatus getFeatureImpactStatus() {
        return featureImpactStatus;
    }

    public void setFeatureImpactStatus(FeatureImpactStatus featureImpactStatus) {
        this.featureImpactStatus = featureImpactStatus;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "summary='" + summary + '\'' +
                ", featureImpactStatus=" + featureImpactStatus +
                '}';
    }
}
