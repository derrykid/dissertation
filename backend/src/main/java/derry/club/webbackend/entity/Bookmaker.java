package derry.club.webbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import derry.club.webbackend.entity.deserializer.BookmakerDeserializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bookmaker {

    @JsonProperty("bookmaker")
    private String name;
    @JsonProperty("home_odd")
    private Float homeOdd;
    @JsonProperty("draw_odd")
    private float drawOdd;
    @JsonProperty("away_odd")
    private float awayOdd;
    @JsonProperty("home_team")
    private String homeTeam;
    @JsonProperty("away_team")
    private String awayTeam;

    @JsonProperty("homeProbability")
    private float homeProbability;

    @JsonProperty("drawProbability")
    private float drawProbability;

    @JsonProperty("awayProbability")
    private float awayProbability;

    private String sport;

    @JsonProperty("match_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime matchTime;

    private List<Float> historyHomeOdds = new ArrayList<>();
    private List<Float> historyDrawOdds = new ArrayList<>();
    private List<Float> historyAwayOdds = new ArrayList<>();

    @Override
    public String toString() {
        return "Bookmaker{" +
                "name='" + name + '\'' +
                ", homeOdd=" + homeOdd +
                ", drawOdd=" + drawOdd +
                ", awayOdd=" + awayOdd +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", sport='" + sport + '\'' +
                ", matchTime=" + matchTime +
                ", historyHomeOdds=" + historyHomeOdds +
                ", historyDrawOdds=" + historyDrawOdds +
                ", historyAwayOdds=" + historyAwayOdds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmaker bookmaker = (Bookmaker) o;

        if (!name.equals(bookmaker.name)) return false;
        if (!homeTeam.equals(bookmaker.homeTeam)) return false;
        if (!awayTeam.equals(bookmaker.awayTeam)) return false;
        if (!sport.equals(bookmaker.sport)) return false;
        return matchTime.equals(bookmaker.matchTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + homeTeam.hashCode();
        result = 31 * result + awayTeam.hashCode();
        result = 31 * result + sport.hashCode();
        result = 31 * result + matchTime.hashCode();
        return result;
    }

    public List<Float> getHistoryHomeOdds() {
        return historyHomeOdds;
    }

    public void setHistoryHomeOdds(List<Float> historyHomeOdds) {
        this.historyHomeOdds = historyHomeOdds;
    }

    public List<Float> getHistoryDrawOdds() {
        return historyDrawOdds;
    }

    public void setHistoryDrawOdds(List<Float> historyDrawOdds) {
        this.historyDrawOdds = historyDrawOdds;
    }

    public List<Float> getHistoryAwayOdds() {
        return historyAwayOdds;
    }

    public void setHistoryAwayOdds(List<Float> historyAwayOdds) {
        this.historyAwayOdds = historyAwayOdds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHomeOdd() {
        return homeOdd;
    }

    public void setHomeOdd(float homeOdd) {
        this.homeOdd = homeOdd;
    }

    public float getDrawOdd() {
        return drawOdd;
    }

    public void setDrawOdd(float drawOdd) {
        this.drawOdd = drawOdd;
    }

    public float getAwayOdd() {
        return awayOdd;
    }

    public void setAwayOdd(float awayOdd) {
        this.awayOdd = awayOdd;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

    public void initializeHistoryOdds() {
        this.historyHomeOdds.add(homeOdd);
        this.historyDrawOdds.add(drawOdd);
        this.historyAwayOdds.add(awayOdd);
    }
}
