package com.example.smartmeeting.MainLogic.DTO.meetings;

import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingDTO implements IMeetingDTO {


    private String meetingName;
    private String creatingUser;
    private LocalDate startDate;
    private LocalTime startTime;
    private boolean priotize;
    private int duration;

    public MeetingDTO(String meetingName, String creatingUser, LocalDate startDate, LocalTime startTime, boolean priotize, int duration){

        this.meetingName = meetingName;
        this.creatingUser = creatingUser;
        this.startDate = startDate;
        this.startTime = startTime;
        this.priotize = priotize;
        this.duration = duration;

    }


    //GETTERS AND SETTERS


    @Override
    public String getMeetingName() {
        return meetingName;
    }

    @Override
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Override
    public String getCreatingUser() {
        return creatingUser;
    }

    @Override
    public void setCreatingUser(String creatingUser) {
        this.creatingUser = creatingUser;
    }

    @Override
    public LocalDate getDate() {
        return startDate;
    }

    @Override
    public void setDate(LocalDate date) {
        this.startDate = date;
    }

    @Override
    public LocalTime getTime() {
        return startTime;
    }

    @Override
    public void setTime(LocalTime time) {
        this.startTime = time;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;

    }

    @Override
    public boolean getPriotize() {
        return priotize;
    }

    @Override
    public void setPriotize(boolean priotize) {
        this.priotize = priotize;
    }



}
