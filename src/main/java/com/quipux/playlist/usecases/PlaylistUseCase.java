package com.quipux.playlist.usecases;

import com.quipux.playlist.models.dto.PlaylistDTO;

import java.util.List;

public interface PlaylistUseCase {
    PlaylistDTO addPlaylist(PlaylistDTO playlistDTO);
    List<PlaylistDTO> getAllPlaylists();
    PlaylistDTO getPlaylistByName(String listname);
    boolean deletePlaylist(String listname);
}

