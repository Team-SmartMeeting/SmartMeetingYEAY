package com.example.smartmeeting.MainLogic.DTO.Topic;


/**
 * @author Søren Aarup Poulsen
 */

public interface ITopic {


    // <editor-folder desc="Properties"

    String getTopicName();

    void setTopicName(String topicName);

    int getTopicDuration();

    void setTopicDuration(int topicDuration);

    String getDescription();

    void setDescription(String description);


    // </editor-folder>

    /*
    ---------------------- Public Methods -----------------------
     */
}
