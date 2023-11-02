package com.quipux.playlist.services;

import com.quipux.playlist.mappers.PlaylistMapper;
import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.entities.Playlist;
import com.quipux.playlist.repositories.PlaylistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    public PlaylistService(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
    }

    public Playlist addPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public List<PlaylistDTO> getAllPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(playlistMapper::playlistToPlaylistDTO)
                .collect(Collectors.toList());
    }

    public PlaylistDTO getPlaylistByName(String listname) {
        Playlist playlist = playlistRepository.findByName(listname);
        if (playlist != null) {
            return playlistMapper.playlistToPlaylistDTO(playlist);
        }
        return null;
    }

    @Transactional
    public boolean deletePlaylist(String listname) {
        if (playlistRepository.findByName(listname)!= null) {
            try {
                playlistRepository.deleteByNombre(listname);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
