package com.example.smartmeeting.MainLogic.DTO.meetings;


import java.time.LocalDate;

/**
 * @author Søren Aarup Poulsen
 */

public interface IMeetingDTO {


    // <editor-folder desc="Properties"

    String getMeetingName();

    void setMeetingName(String meetingName);

    String getCreatingUser();

    void setCreatingUser(String creatingUser);

    LocalDate getDate();

    void setDate(LocalDate date);

    // </editor-folder>

    /*
    ---------------------- Public Methods -----------------------
     */
}
