package org.brittlawson.model;

import java.time.LocalDate;

public class Jump {

    private int jumpNumber;
    private LocalDate date;
    private int dzId;
    private String aircraft;
    private int exitAltitudeInFeet;
    private int deploymentAltitudeInFeet;
    private int canopyId;
    private Integer distanceFromTargetFeet;
    private String maneuver;
    private String description;

    public int getJumpNumber() {
        return jumpNumber;
    }

    public void setJumpNumber(int jumpNumber) {
        this.jumpNumber = jumpNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDzId() {
        return dzId;
    }

    public void setDzId(int dzId) {
        this.dzId = dzId;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public int getExitAltitudeInFeet() {
        return exitAltitudeInFeet;
    }

    public void setExitAltitudeInFeet(int exitAltitudeInFeet) {
        this.exitAltitudeInFeet = exitAltitudeInFeet;
    }

    public int getDeploymentAltitudeInFeet() {
        return deploymentAltitudeInFeet;
    }

    public void setDeploymentAltitudeInFeet(int deploymentAltitudeInFeet) {
        this.deploymentAltitudeInFeet = deploymentAltitudeInFeet;
    }

    public int getCanopyId() {
        return canopyId;
    }

    public void setCanopyId(int canopyId) {
        this.canopyId = canopyId;
    }

    public Integer getDistanceFromTargetFeet() {
        return distanceFromTargetFeet;
    }

    public void setDistanceFromTargetFeet(Integer distanceFromTargetFeet) {
        this.distanceFromTargetFeet = distanceFromTargetFeet;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
