package derry.club.webbackend.receiver;


import derry.club.webbackend.controller.OddsController;
import derry.club.webbackend.entity.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            this.address = new URI(containerAddress);
            this.matchPrediction = new HashMap<>();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void predict(Set<Match> matchSet) {
        // send request the container, and get result, also update the web by design pattern
        logger.info("Requesting model with data {}", matchSet);

        HttpRequest request = HttpRequest.newBuilder()
                // this uri should be read from docker, env
                .uri(address)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
//                        preparedDataForModel.toString()
                        "Hello"
                ))
                .build();

        try {
            HttpResponse<String> response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // update the web
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            logger.info("exception occurred");
            throw new RuntimeException(e);
        }

    }
}
