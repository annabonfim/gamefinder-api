package br.com.fiap.gamefinderapi;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "https://api.igdb.com/v4/games", accept = "application/json")
public interface IgdbClient {
    @PostExchange
    String search(
            @RequestHeader("Client-ID") String clientId,
            @RequestHeader("Authorization") String authorization,
            @RequestBody String query
    );
}