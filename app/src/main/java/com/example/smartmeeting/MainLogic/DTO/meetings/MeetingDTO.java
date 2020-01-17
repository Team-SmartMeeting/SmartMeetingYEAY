package com.example.smartmeeting.MainLogic.DTO.meetings;

import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;

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
    private ArrayList<Topic> Agendalist;
    private ArrayList<String> inviteUserList;
    private int meetingStatus;
    private int agendaStatus;

    public MeetingDTO(){

    }


    public MeetingDTO(String meetingName, String startTime, String startDate, int duration){
        this.meetingName = meetingName;
        this.startTime = startTime;
        this.startDate = startDate;
        this.duration = duration;

    }

    public MeetingDTO(String meetingName, String creatingUser, ArrayList <Topic> Agendalist, int meetingStatus, int agendaStatus){
        this.meetingName = meetingName;
        this.creatingUser = creatingUser;
        this.Agendalist = Agendalist;
        this.meetingStatus = meetingStatus;
        this.agendaStatus = agendaStatus;
    }

    public MeetingDTO(String meetingName, String startDate, String startTime, boolean priotize, int duration, int meetingStatus, int agendaStatus){

        this.meetingName = meetingName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.priotize = priotize;
        this.duration = duration;
        this.meetingStatus = meetingStatus;
        this.agendaStatus = agendaStatus;
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


    public ArrayList<Topic> getAgendalist() {
        return Agendalist;
    }

    public void setAgendalist(ArrayList<Topic> agendalist) {
        Agendalist = agendalist;
    }

    @Override
    public ArrayList<String> getInviteUserList() {
        return inviteUserList;
    }

    @Override
    public void setInviteUserList(ArrayList<String> inviteUserList) {
        this.inviteUserList = inviteUserList;
    }

    public int getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(int meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public int getAgendaStatus() {
        return agendaStatus;
    }

    public void setAgendaStatus(int agendaStatus) {
        this.agendaStatus = agendaStatus;
    }
}
