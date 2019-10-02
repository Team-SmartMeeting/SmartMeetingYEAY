package com.example.smartmeeting.meetings;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IMeetingDTO {

    /**
     * @author Søren Aarup Poulsen
     */

        /*
    ---------------------- Properties -----------------------
     */

    String getName();
    void setName(String name);

    String getLocation();
    void setLocation(String location);

    LocalDate getDate();
    void setDate(LocalDate date);

    LocalTime getTime();
    void setTime(LocalTime time);

    int getDurationInMin();
    void setDurationInMin(int durationInMin);

    boolean getPrioritizeTopic();
    void setPrioritizeTopic(boolean prioritizeTopic);


}
