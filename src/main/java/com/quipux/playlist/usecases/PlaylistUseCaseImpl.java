package com.quipux.playlist.usecases;

import com.quipux.playlist.mappers.PlaylistMapper;
import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.entities.Playlist;
import com.quipux.playlist.services.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistUseCaseImpl implements PlaylistUseCase {

    private final PlaylistService playlistService;
    private final PlaylistMapper playlistMapper;

    public PlaylistUseCaseImpl(PlaylistService playlistService, PlaylistMapper playlistMapper) {
        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public PlaylistDTO addPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = playlistMapper.playlistDTOToPlaylist(playlistDTO);
        Playlist createdPlaylist = playlistService.addPlaylist(playlist);

        return playlistMapper.playlistToPlaylistDTO(createdPlaylist);
    }

    @Override
    public List<PlaylistDTO> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @Override
    public PlaylistDTO getPlaylistByName(String listname) {
        return playlistService.getPlaylistByName(listname);
    }

    @Override
    public boolean deletePlaylist(String listname) {
        return playlistService.deletePlaylist(listname);
    }
}

