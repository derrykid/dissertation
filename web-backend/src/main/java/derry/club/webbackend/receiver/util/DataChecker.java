package derry.club.webbackend.receiver.util;

import derry.club.webbackend.controller.OddsController;
import derry.club.webbackend.entity.Bookmaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataChecker {
    private static final Logger logger = LoggerFactory.getLogger(DataChecker.class);
    public static boolean check(List<Bookmaker> books) {
        try {
            Set<Bookmaker> availableBooks = new HashSet<>();
            for (Bookmaker book : books) {
                if (book.getName().isBlank()) {
                    continue;
                }
                if (book.getHomeOdd() == 0.0) {
                    continue;
                }
                availableBooks.add(book);
            }

            return true;

        } catch (IllegalArgumentException ex) {
            logger.debug(books.toString());
            return false;
        }
    }
}
