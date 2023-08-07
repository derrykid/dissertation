package derry.club.webbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
    @JsonProperty("homeProbability")
    private float homeProbability;

    @JsonProperty("drawProbability")
    private float drawProbability;

    @JsonProperty("awayProbability")
    private float awayProbability;

    public float getHomeProbability() {
        return homeProbability;
    }

    public void setHomeProbability(float homeProbability) {
        this.homeProbability = homeProbability;
    }

    public float getDrawProbability() {
        return drawProbability;
    }

    public void setDrawProbability(float drawProbability) {
        this.drawProbability = drawProbability;
    }

    public float getAwayProbability() {
        return awayProbability;
    }

    public void setAwayProbability(float awayProbability) {
        this.awayProbability = awayProbability;
    }
}
