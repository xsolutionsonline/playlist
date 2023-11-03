package com.quipux.playlist.controller;

import com.quipux.playlist.controllers.PlaylistController;
import com.quipux.playlist.controllers.SpotifyAuthController;
import com.quipux.playlist.usecases.PlaylistUseCase;
import com.quipux.playlist.usecases.SpotifyUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SpotifyAuthControllerTest {

    @Mock
    private SpotifyUseCase spotifyUseCase;

    @InjectMocks
    private SpotifyAuthController spotifyAuthController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSpotifyCallback_ReturnsAuthorizationCodeMessage() {

        String authorizationCode = "testAuthorizationCode";
        String result = spotifyAuthController.spotifyCallback(authorizationCode);

        assertEquals("Authorization code received: " + authorizationCode, result);
    }

    @Test
    public void testGetSpotifyGenres_ReturnsGenresAndOKStatus() {
        List<String> expectedGenres = new ArrayList<>();
        expectedGenres.add("Pop");
        expectedGenres.add("Rock");

        when(spotifyUseCase.getSpotifyGenres()).thenReturn(expectedGenres);

        ResponseEntity<List<String>> responseEntity = spotifyAuthController.getSpotifyGenres();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedGenres, responseEntity.getBody());
    }

    @Test
    public void testGetSpotifyGenres_ReturnsInternalServerError() {

        when(spotifyUseCase.getSpotifyGenres()).thenReturn(new ArrayList<>());

        ResponseEntity<List<String>> responseEntity = spotifyAuthController.getSpotifyGenres();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
