package derry.club.webbackend.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import derry.club.webbackend.controller.OddsController;
import derry.club.webbackend.entity.Bookmaker;
import derry.club.webbackend.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Receive the data from the Selenium script container
 */
@RestController
public class DataReceiver {
    private final Logger logger = LoggerFactory.getLogger(OddsController.class);
    private final ModelPredictor modelPredictor;
    private static Set<Match> matchSet = new HashSet<>();

    public static Set<Match> getMatchSet() {
        return matchSet;
    }

    public DataReceiver(@Autowired ModelPredictor modelPredictor) {
        this.modelPredictor = modelPredictor;
    }

    @CrossOrigin(origins = {"http://localhost:63342", "http://frontend"})
    @PostMapping(path = "/chartData")
    public Set<Match> chartData() {
        return matchSet.size() != 0 ? matchSet : new HashSet<>();
    }

    @PostMapping(path = "/data")
    public void receiver(@RequestBody List<Bookmaker> books) {
        logger.info("Coming data:{}", books);

        DataConverter.appendOdds(books);

//        modelPredictor.predict(matchSet);
    }
}
