package com.quipux.playlist.mappers;

import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.entities.Playlist;
import org.springframework.stereotype.Component;

public interface PlaylistMapper {
    PlaylistDTO playlistToPlaylistDTO(Playlist playlist);
    Playlist playlistDTOToPlaylist(PlaylistDTO playlistDTO);
}
