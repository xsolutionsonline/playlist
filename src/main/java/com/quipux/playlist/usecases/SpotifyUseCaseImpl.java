package com.quipux.playlist.usecases;

import com.quipux.playlist.services.PlaylistService;
import com.quipux.playlist.services.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpotifyUseCaseImpl implements SpotifyUseCase{
    @Autowired
    SpotifyService spotifyService;

    @Override
    public List<String> getSpotifyGenres() {
        return spotifyService.getSpotifyGenres();
    }
}
