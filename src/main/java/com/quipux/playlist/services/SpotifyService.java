package com.quipux.playlist.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    @Value("${spotify.tokenEndpoint}")
    private String tokenEndpoint;

    @Value("${spotify.genreEndpoint}")
    private String genreEndpoint;

    public List<String> getSpotifyGenres() {
        RestTemplate restTemplate = new RestTemplate();

        String accessToken = getAccessToken(restTemplate);

        if (accessToken != null && !accessToken.isEmpty()) {
            ResponseEntity<String> genresResponse = getGenresResponse(restTemplate, accessToken);

            if (genresResponse.getStatusCodeValue() == HttpStatus.OK.value()) {
                return processGenresResponse(genresResponse.getBody());
            } else {
                return List.of("Error al obtener los géneros. Código de estado: " + genresResponse.getStatusCodeValue());
            }
        } else {
            return List.of("Error al obtener el token de acceso.");
        }
    }

    private String getAccessToken(RestTemplate restTemplate) {
        HttpHeaders headers = createHeadersWithClientCredentials();

        MultiValueMap<String, String> requestBody = createAccessTokenRequestBody();

        ResponseEntity<String> response = restTemplate.exchange(
                tokenEndpoint,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
        );

        if (response.getStatusCodeValue() == HttpStatus.OK.value()) {
            return extractAccessToken(response.getBody());
        } else {
            return null;
        }
    }

    private HttpHeaders createHeadersWithClientCredentials() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        return headers;
    }

    private MultiValueMap<String, String> createAccessTokenRequestBody() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        return requestBody;
    }

    private String extractAccessToken(String responseBody) {
        String accessToken = "";

        try {
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            accessToken = jsonResponse.get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    private ResponseEntity<String> getGenresResponse(RestTemplate restTemplate, String accessToken) {
        HttpHeaders headersWithToken = createHeadersWithAccessToken(accessToken);

        return restTemplate.exchange(
                genreEndpoint,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                String.class
        );
    }

    private HttpHeaders createHeadersWithAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private List<String> processGenresResponse(String responseBody) {
        List<String> genres = new ArrayList<>();

        try {
            JsonElement jsonElement = JsonParser.parseString(responseBody);
            JsonObject jsonResponse = jsonElement.getAsJsonObject();

            JsonArray genresArray = jsonResponse.getAsJsonArray("genres");

            for (JsonElement element : genresArray) {
                genres.add(element.getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }
}
