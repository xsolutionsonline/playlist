package com.quipux.playlist.usecases;

import org.springframework.stereotype.Component;
import java.util.List;


public interface SpotifyUseCase {

    List<String> getSpotifyGenres();
}
