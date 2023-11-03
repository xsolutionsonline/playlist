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
        List<SongDTO> songDTOs = playlist.getSongs().stream()
                .map(songMapper::songToSongDTO)
                .collect(Collectors.toList());

        return PlaylistDTO.builder()
                .name(playlist.getName())
                .description(playlist.getDescription())
                .songs(songDTOs)
                .build();
    }

    @Override
    public Playlist playlistDTOToPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = Playlist.builder()
                .name(playlistDTO.getName())
                .description(playlistDTO.getDescription())
                .build();

        List<Song> songs = playlistDTO.getSongs().stream()
                .map(songMapper::songDTOToSong)
                .collect(Collectors.toList());

        playlist.setSongs(songs);
        return playlist;
    }
}

