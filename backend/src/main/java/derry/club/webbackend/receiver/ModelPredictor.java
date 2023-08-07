package derry.club.webbackend.receiver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import derry.club.webbackend.entity.Book;
import derry.club.webbackend.entity.Bookmaker;
import derry.club.webbackend.entity.Match;
import derry.club.webbackend.entity.MatchPrediction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * Send the data to model container and get the prediction
 */
@org.springframework.stereotype.Service
public class ModelPredictor {

    private final URI address;
    private final Logger logger = LoggerFactory.getLogger(ModelPredictor.class);
    private final Map<Match, Float> matchPrediction;

    public ModelPredictor(@Value("${model.address}") String containerAddress) {
        try {
            logger.info("Container address: {}", containerAddress);
            this.address = new URI(containerAddress + "/predict");
            this.matchPrediction = new HashMap<>();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<MatchPrediction> predict(Set<Match> matchSet) {

        logger.info("Requesting model with data {}", matchSet);

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        Set<MatchPrediction> matchPredictions = new HashSet<>();

        for (Match per : matchSet) {

            Optional<Bookmaker> optionalBookmaker = per.getBookmakers().stream().findAny();

            if (optionalBookmaker.isEmpty()) {
                continue;
            }

            Bookmaker oneBookmaker = optionalBookmaker.get();

            Book book = new Book();
            book.setHomeProbability(oneBookmaker.getHomeProbability());
            book.setDrawProbability(oneBookmaker.getDrawProbability());
            book.setAwayProbability(oneBookmaker.getAwayProbability());

            String json = null;
            try {
                json = objectMapper.writeValueAsString(book);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            var response = template.postForObject(address, entity, String.class);


            System.out.println("=========One prediction book start============");
            System.out.println(oneBookmaker.getHomeTeam());
            System.out.println(oneBookmaker.getAwayTeam());
            System.out.println(oneBookmaker.getMatchTime());
            Float homeWinrate = Float.parseFloat(response);
            System.out.println(response);
            System.out.println("=========One prediction book end============");

            MatchPrediction pred = new MatchPrediction();
            pred.setHomeTeam(oneBookmaker.getHomeTeam());
            pred.setAwayTeam(oneBookmaker.getAwayTeam());
            pred.setMatchTime(oneBookmaker.getMatchTime());
            pred.setWinrate(homeWinrate);

            matchPredictions.add(pred);
        }


        return matchPredictions;
    }
}
