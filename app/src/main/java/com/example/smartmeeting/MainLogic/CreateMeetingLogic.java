package com.example.smartmeeting.MainLogic;


import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;

public class CreateMeetingLogic {

    private MeetingDTO meeting;

    public CreateMeetingLogic(String meetingName, String startDate, String startTime, boolean priotize, int duration){
        meeting = new MeetingDTO(meetingName, startDate, startTime, priotize, duration);
    }

    public

}
