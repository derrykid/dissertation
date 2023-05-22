package derry.club.webbackend.entity;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Match {
    private String sport;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime matchTime = LocalDateTime.now();
    private List<Bookmaker> bookmakers;

    @Override
    public String toString() {
        return "Match{" +
                "sport='" + sport + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", matchTime=" + matchTime +
                ", bookmakers=" + bookmakers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (!Objects.equals(sport, match.sport)) return false;
        if (!Objects.equals(homeTeam, match.homeTeam)) return false;
        if (!Objects.equals(awayTeam, match.awayTeam)) return false;
        return Objects.equals(matchTime, match.matchTime);
    }

    @Override
    public int hashCode() {
        int result = sport != null ? sport.hashCode() : 0;
        result = 31 * result + (homeTeam != null ? homeTeam.hashCode() : 0);
        result = 31 * result + (awayTeam != null ? awayTeam.hashCode() : 0);
        result = 31 * result + (matchTime != null ? matchTime.hashCode() : 0);
        return result;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

    public List<Bookmaker> getBookmakers() {
        return bookmakers;
    }

    public void setBookmakers(List<Bookmaker> bookmakers) {
        this.bookmakers = bookmakers;
    }

}
