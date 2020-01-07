package com.example.smartmeeting.MainLogic.DTO.meetings;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Søren Aarup Poulsen
 */

public interface IMeetingDTO {


    // <editor-folder desc="Properties"

    String getMeetingName();

    void setMeetingName(String meetingName);

    String getCreatingUser();

    void setCreatingUser(String creatingUser);

    String getDate();

    void setDate(String date);

    String getTime();

    void setTime(String time);

    int getDuration();

    void setDuration(int duration);

    boolean getPriotize();

    void setPriotize(boolean priotize);

    // </editor-folder>

    /*
    ---------------------- Public Methods -----------------------
     */
}
