package com.quipux.playlist.mappers;

import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.dto.SongDTO;
import com.quipux.playlist.models.entities.Playlist;
import com.quipux.playlist.models.entities.Song;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistMapperImpl implements PlaylistMapper {

    private final SongMapper songMapper;

    public PlaylistMapperImpl(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    @Override
    public PlaylistDTO playlistToPlaylistDTO(Playlist playlist) {
        List<SongDTO> songDTOs = playlist.getCanciones().stream()
                .map(songMapper::songToSongDTO)
                .collect(Collectors.toList());

        return PlaylistDTO.builder()
                .id(playlist.getId())
                .nombre(playlist.getNombre())
                .descripcion(playlist.getDescripcion())
                .canciones(songDTOs)
                .build();
    }

    @Override
    public Playlist playlistDTOToPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = new Playlist();
        playlist.setId(playlistDTO.getId());
        playlist.setNombre(playlistDTO.getNombre());
        playlist.setDescripcion(playlistDTO.getDescripcion());

        List<Song> songs = playlistDTO.getCanciones().stream()
                .map(songMapper::songDTOToSong)
                .collect(Collectors.toList());

        playlist.setCanciones(songs);
        return playlist;
    }
}

