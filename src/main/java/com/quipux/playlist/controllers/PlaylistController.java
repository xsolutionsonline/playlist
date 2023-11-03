package com.quipux.playlist.controllers;

import com.quipux.playlist.config.constants.PlaylistConstants;
import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.usecases.PlaylistUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

    private final PlaylistUseCase playlistUseCase;

    public PlaylistController(PlaylistUseCase playlistUseCase) {
        this.playlistUseCase = playlistUseCase;
    }

    @PostMapping
    public ResponseEntity<?> addNewPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        try {
            validatePlaylistDTO(playlistDTO);
            PlaylistDTO createdPlaylistDTO = playlistUseCase.addPlaylist(playlistDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPlaylistDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        List<PlaylistDTO> playlists = playlistUseCase.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{listname}")
    public ResponseEntity<?> getPlaylistByName(@PathVariable String listname) {
        PlaylistDTO playlist = playlistUseCase.getPlaylistByName(listname);
        return playlist != null ? ResponseEntity.ok(playlist) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(PlaylistConstants.PLAYLIST_NOT_FOUND);
    }

    @DeleteMapping("/{listname}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String listname) {
        boolean deleted = playlistUseCase.deletePlaylist(listname);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(PlaylistConstants.PLAYLIST_NOT_FOUND);
    }

    private void validatePlaylistDTO(PlaylistDTO playlistDTO) {
        if (playlistDTO.getName() == null || playlistDTO.getName().isEmpty() ||
                playlistDTO.getDescription() == null || playlistDTO.getDescription().isEmpty()) {

            throw new IllegalArgumentException(PlaylistConstants.INVALID_NAME_OR_DESCRITPION);
        }

        if(playlistDTO.getSongs().stream().anyMatch(songDTO ->
                songDTO.getTitle() == null || songDTO.getTitle().isEmpty() ||
                        songDTO.getArtist() == null || songDTO.getArtist().isEmpty() ||
                        songDTO.getAlbum() == null || songDTO.getAlbum().isEmpty() ||
                        songDTO.getYear() == null || songDTO.getYear().isEmpty() ||
                        songDTO.getGenre() == null || songDTO.getGenre().isEmpty())) {

            throw new IllegalArgumentException(PlaylistConstants.INVALID_FIELD_SONG);
        }
    }
}
