package com.quipux.playlist.controllers;

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
        if (playlistDTO.getNombre() == null || playlistDTO.getCanciones().stream().anyMatch(songDTO -> songDTO.getTitulo().isEmpty())) {
            return ResponseEntity.badRequest().body("El nombre de la lista o el título de la canción no pueden ser nulos o vacíos");
        }

        PlaylistDTO createdPlaylistDTO = playlistUseCase.addPlaylist(playlistDTO);

        if (createdPlaylistDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPlaylistDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo crear la lista de reproducción");
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

        if (playlist != null) {
            return ResponseEntity.ok(playlist);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
    }


    @DeleteMapping("/{listname}")
    public ResponseEntity<?> deletePlaylist(@PathVariable String listname) {
        boolean deleted = playlistUseCase.deletePlaylist(listname);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
    }
}
