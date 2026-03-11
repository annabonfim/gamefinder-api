package br.com.fiap.gamefinderapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class GameService {

    private final IgdbClient igdbClient;
    private final RestClient restClient = RestClient.create();

    @Value("${igdb.client-id}")
    private String clientId;

    @Value("${igdb.client-secret}")
    private String clientSecret;

    public GameService(IgdbClient igdbClient) {
        this.igdbClient = igdbClient;
    }

    public GameResponse recommend(String name) {
        String accessToken = getAccessToken();
        String bearer = "Bearer " + accessToken;

        String query = """
                search "%s";
                fields name,rating,rating_count,first_release_date;
                limit 1;
                """.formatted(safe(name));

        String json = igdbClient.search(clientId, bearer, query);
        System.out.println(json);

        if (json == null || json.trim().equals("[]")) {
            return new GameResponse(
                    "Jogo não encontrado",
                    0.0,
                    0,
                    0,
                    "Nenhuma recomendação disponível"
            );
        }

        String gameName = extractStringValue(json, "\"name\":\"");
        double rating100 = extractDouble(json, "\"rating\":", 0.0);
        double rating = rating100 / 20.0;
        int ratingCount = (int) extractDouble(json, "\"rating_count\":", 0);

        long releaseEpoch = (long) extractDouble(json, "\"first_release_date\":", 0);
        int releaseYear = 0;
        boolean last10Years = false;

        if (releaseEpoch > 0) {
            releaseYear = Instant.ofEpochSecond(releaseEpoch)
                    .atZone(ZoneId.systemDefault())
                    .getYear();

            int currentYear = Instant.now()
                    .atZone(ZoneId.systemDefault())
                    .getYear();

            last10Years = (currentYear - releaseYear) <= 10;
        }

        if (rating100 == 0.0 || ratingCount == 0) {
            return new GameResponse(
                    gameName != null ? gameName : name,
                    0.0,
                    ratingCount,
                    releaseYear,
                    "Dados insuficientes para recomendar"
            );
        }

        boolean manyReviews = ratingCount >= 1000;

        String recommendation;
        if (rating < 3.5) {
            recommendation = "Melhor ver um filme";
        } else if (rating >= 4.5 && manyReviews && last10Years) {
            recommendation = "Altamente recomendado";
        } else {
            recommendation = "Vale a pena";
        }

        return new GameResponse(
                gameName != null ? gameName : name,
                roundToOneDecimal(rating),
                ratingCount,
                releaseYear,
                recommendation
        );
    }

    private String getAccessToken() {
        String url = "https://id.twitch.tv/oauth2/token"
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=client_credentials";

        String body = restClient.post()
                .uri(url)
                .retrieve()
                .body(String.class);

        String token = extractStringValue(body, "\"access_token\":\"");

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Falha ao gerar token da Twitch.");
        }

        return token;
    }

    private static String safe(String s) {
        return s == null ? "" : s.replace("\"", "");
    }

    private static String extractStringValue(String json, String prefix) {
        int i = json.indexOf(prefix);
        if (i < 0) return null;

        int start = i + prefix.length();
        int end = json.indexOf('"', start);
        if (end < 0) return null;

        return json.substring(start, end);
    }

    private static double extractDouble(String json, String prefix, double def) {
        int i = json.indexOf(prefix);
        if (i < 0) return def;

        int start = i + prefix.length();

        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        int end = start;

        while (end < json.length() && "0123456789.-".indexOf(json.charAt(end)) >= 0) {
            end++;
        }

        try {
            return Double.parseDouble(json.substring(start, end));
        } catch (Exception e) {
            return def;
        }
    }

    private static double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}