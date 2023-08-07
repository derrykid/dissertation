package derry.club.webbackend.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class MatchPrediction {

    private String homeTeam;
    private String awayTeam;
    private float winrate;
    private LocalDateTime matchTime;

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

    public float getWinrate() {
        return winrate;
    }

    public void setWinrate(float winrate) {
        this.winrate = winrate;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

    @Override
    public String toString() {
        return "MatchPrediction{" +
                "homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", winrate=" + winrate +
                ", matchTime=" + matchTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchPrediction that = (MatchPrediction) o;

        if (Float.compare(that.winrate, winrate) != 0) return false;
        if (!Objects.equals(homeTeam, that.homeTeam)) return false;
        if (!Objects.equals(awayTeam, that.awayTeam)) return false;
        return Objects.equals(matchTime, that.matchTime);
    }

    @Override
    public int hashCode() {
        int result = homeTeam != null ? homeTeam.hashCode() : 0;
        result = 31 * result + (awayTeam != null ? awayTeam.hashCode() : 0);
        result = 31 * result + (winrate != +0.0f ? Float.floatToIntBits(winrate) : 0);
        result = 31 * result + (matchTime != null ? matchTime.hashCode() : 0);
        return result;
    }
}
