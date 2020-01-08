package com.example.smartmeeting.MainLogic.DTO.meetings;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author Søren Aarup Poulsen
 */

public class MeetingDTO implements IMeetingDTO {


    private String meetingName;
    private String creatingUser;
    private String startDate;
    private String startTime;
    private boolean priotize;
    private int duration;
    private ArrayList<String> Agendalist;

    public MeetingDTO(String meetingName, String startDate, String startTime, boolean priotize, int duration){

        this.meetingName = meetingName;
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
    public String getDate() {
        return startDate;
    }

    @Override
    public void setDate(String date) {
        this.startDate = date;
    }

    @Override
    public String getTime() {
        return startTime;
    }

    @Override
    public void setTime(String time) {
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


    public ArrayList<String> getAgendalist() {
        return Agendalist;
    }

    public void setAgendalist(ArrayList<String> agendalist) {
        Agendalist = agendalist;
    }
}
