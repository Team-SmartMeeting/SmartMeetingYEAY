package com.example.smartmeeting.meetings;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Søren Aarup Poulsen
 */

public class MeetingDTO implements IMeetingDTO {



    /*
    -------------------------- Fields --------------------------
     */
    private String name;
    private String location;
    private LocalDate date;
    private  LocalTime time;
    private int durationInMin;
    private boolean prioritizeTopic;


    /*
    ----------------------- Constructor -------------------------
     */

    public MeetingDTO(String name, String location, LocalDate date, LocalTime time){
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
    }



    /*
    ------------------------ Properties -------------------------
     */

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalTime getTime() {
        return time;
    }

    @Override
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public int getDurationInMin() {
        return durationInMin;
    }

    @Override
    public void setDurationInMin(int durationInMin) {
        this.durationInMin = durationInMin;
    }

    @Override
    public boolean getPrioritizeTopic() {
        return prioritizeTopic;
    }

    @Override
    public void setPrioritizeTopic(boolean prioritizeTopic) {
        this.prioritizeTopic = prioritizeTopic;
    }
}
