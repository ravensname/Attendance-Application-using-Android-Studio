package com.example.projectabsent;

public class Attendance {
    private String name;
    private String timeAndDate;
    private String position;
    private String info;

    public Attendance(String name, String timeAndDate, String position, String info) {
        this.name = name;
        this.timeAndDate = timeAndDate;
        this.position = position;
        this.info = info;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public String getPosition() {
        return position;
    }

    public String getInfo() {
        return info;
    }

    // Setter methods (if needed)
    public void setName(String name) {
        this.name = name;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
