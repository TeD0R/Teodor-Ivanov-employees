package com.example.demo.models;


import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Employee {
    private int id;

    private int projectId;

    private Date dateFrom;

    private Date dateTo;

    private final long hours;

    private final long days;

    public Employee(int id, int projectId, Date dateFrom, Date dateTo) {
        this.id = id;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        long duration = dateTo.getTime() - dateFrom.getTime();
        hours = TimeUnit.MILLISECONDS.toHours(duration);
        days = TimeUnit.MILLISECONDS.toDays(duration);
    }

    public long getHours() {
        return hours;
    }

    public long getDays() {
        return days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String toString() {
        return projectId + " ^^ " + hours + " ^^ " + id;
    }

}
