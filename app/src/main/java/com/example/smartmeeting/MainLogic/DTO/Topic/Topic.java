package com.example.smartmeeting.MainLogic.DTO.Topic;

import java.util.ArrayList;

/**
 * @author Søren Aarup Poulsen
 */

public class Topic implements ITopic {


    private String topicName;
    private String description;
    private int topicDuration;

    public Topic(String topicName, String description, int topicDuration){

        this.topicName = topicName;
        this.description = description;
        this.topicDuration = topicDuration;

    }



    //GETTERS AND SETTERS

    @Override
    public String getTopicName() {
        return topicName;
    }

    @Override
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public int getTopicDuration() {
        return topicDuration;
    }

    @Override
    public void setTopicDuration(int topicDuration) {
        this.topicDuration = topicDuration;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
