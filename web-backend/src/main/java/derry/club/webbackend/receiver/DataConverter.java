package derry.club.webbackend.receiver;

import derry.club.webbackend.entity.Bookmaker;
import derry.club.webbackend.entity.Match;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Convert the data to win rate probability
 */
public class DataConverter {
    public static void appendOdds(List<Bookmaker> books) {

        Set<Match> matchSet = DataReceiver.getMatchSet();

        for (Bookmaker bookmaker : books) {

            LocalDateTime matchTime = bookmaker.getMatchTime();
            String homeTeam = bookmaker.getHomeTeam();
            String awayTeam = bookmaker.getAwayTeam();

            if (matchSet.size() == 0) {
                Match match = createMatchAddBookmaker(bookmaker);
                matchSet.add(match);
            } else {

                Optional<Match> optionalMatch = matchSet.stream().filter(e ->
                        e.getMatchTime().equals(matchTime) &&
                                e.getHomeTeam().equals(homeTeam) &&
                                e.getAwayTeam().equals(awayTeam)).findAny();

                if (optionalMatch.isPresent()) {
                    updateThisMatchWithBookmaker(optionalMatch.get(), bookmaker);
                } else {
                    Match match = createMatchAddBookmaker(bookmaker);
                    matchSet.add(match);
                }
            }
        }
    }

    private static void updateThisMatchWithBookmaker(Match match, Bookmaker bookmaker) {

        List<Bookmaker> oneMatchBookmakers = match.getBookmakers();
        Optional<Bookmaker> bookmakerOptional = oneMatchBookmakers.stream()
                .filter(e -> e.equals(bookmaker)).findAny();

        if (bookmakerOptional.isEmpty()) {
            bookmaker.initializeHistoryOdds();
            oneMatchBookmakers.add(bookmaker);
        } else {

            Bookmaker bookToUpdate = bookmakerOptional.get();

            // means the history odds is not initialized
            if (bookmaker.getHistoryAwayOdds().size() == 0) {
                bookToUpdate.getHistoryHomeOdds().add(bookmaker.getHomeOdd());
                bookToUpdate.getHistoryDrawOdds().add(bookmaker.getDrawOdd());
                bookToUpdate.getHistoryAwayOdds().add(bookmaker.getAwayOdd());
            }

        }

    }

    private static Match createMatchAddBookmaker(Bookmaker book) {

        Match match = new Match();

        match.setSport(book.getSport());
        match.setMatchTime(book.getMatchTime());
        match.setHomeTeam(book.getHomeTeam());
        match.setAwayTeam(book.getAwayTeam());

        List<Bookmaker> bookmakers = new ArrayList<>();
        book.initializeHistoryOdds();
        bookmakers.add(book);
        match.setBookmakers(bookmakers);

        return match;
    }

}
