package com.quipux.playlist.services;

import com.quipux.playlist.services.SpotifyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {
        "spotify.clientId=testClientId",
        "spotify.clientSecret=testClientSecret",
        "spotify.tokenEndpoint=http://test-token-endpoint.com",
        "spotify.genreEndpoint=http://test-genre-endpoint.com"
})
public class SpotifyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SpotifyService spotifyService;

   // @Test
    public void testGetSpotifyGenres_Successful() {
        // Simular respuestas con valores de las propiedades
        String accessToken = "mockedAccessToken";
        ResponseEntity<String> mockedTokenResponse = new ResponseEntity<>("{\"access_token\":\"" + accessToken + "\"}", HttpStatus.OK);
        ResponseEntity<String> mockedGenresResponse = new ResponseEntity<>("{\"genres\":[\"Rock\", \"Pop\"]}", HttpStatus.OK);

        // Simulación de la llamada a la propiedad utilizando los valores
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(mockedTokenResponse, mockedGenresResponse);

        List<String> expectedGenres = List.of("Rock", "Pop");

        List<String> result = spotifyService.getSpotifyGenres();

        assertEquals(expectedGenres, result);
    }


    public void testGetSpotifyGenres_FailedToGetToken() {
        ResponseEntity<String> mockedTokenResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(mockedTokenResponse);

        List<String> result = spotifyService.getSpotifyGenres();

        assertEquals(List.of("Error al obtener el token de acceso."), result);
    }

   // @Test
    public void testGetSpotifyGenres_FailedToGetGenres() {
        String accessToken = "mockedAccessToken";
        ResponseEntity<String> mockedTokenResponse = new ResponseEntity<>("{\"access_token\":\"" + accessToken + "\"}", HttpStatus.OK);
        ResponseEntity<String> mockedGenresResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(mockedTokenResponse, mockedGenresResponse);

        List<String> result = spotifyService.getSpotifyGenres();

        assertEquals(List.of("Error al obtener los géneros. Código de estado: 500"), result);
    }
}
