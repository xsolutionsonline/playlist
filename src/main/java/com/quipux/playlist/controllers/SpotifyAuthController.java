package com.quipux.playlist.controllers;

import com.quipux.playlist.usecases.SpotifyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class SpotifyAuthController {


    @Autowired
    SpotifyUseCase spotifyUseCase;

    @GetMapping("/spotify/callback")
    public String spotifyCallback(@RequestParam("code") String authorizationCode) {
        return "Authorization code received: " + authorizationCode;
    }

    @GetMapping("/get-spotify-genres")
    public ResponseEntity<List<String>> getSpotifyGenres() {
        List<String> genres = spotifyUseCase.getSpotifyGenres();

        HttpStatus status = genres.isEmpty() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;

        return new ResponseEntity<>(genres, status);
    }
}