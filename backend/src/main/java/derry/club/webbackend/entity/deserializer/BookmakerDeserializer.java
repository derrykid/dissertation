package derry.club.webbackend.entity.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import derry.club.webbackend.entity.Bookmaker;

import java.io.IOException;

public class BookmakerDeserializer extends JsonDeserializer<Bookmaker> {
    @Override
    public Bookmaker deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        for (JsonNode node : root) {


        String sport = node.get("sport").asText();
        String bookmakerName = node.get("bookmaker").asText();
        String homeTeam = node.get("home_team").asText();
        String awayTeam = node.get("away_team").asText();
        float homeOdd = node.get("home_odd").asLong();
        float drawOdd = node.get("draw_odd").asLong();
        float awayOdd = node.get("away_odd").asLong();
        float homeProbability = node.get("homeProbability").asLong();
        float drawProbability = node.get("drawProbability").asLong();
        float awayProbability = node.get("awayProbability").asLong();

        String matchTime = node.get("match_time").asText();

        }
        return new Bookmaker();
    }
}
