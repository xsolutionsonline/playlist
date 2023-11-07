package com.quipux.playlist.services;

import com.quipux.playlist.mappers.PlaylistMapper;
import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.entities.Playlist;
import com.quipux.playlist.repositories.PlaylistRepository;
import com.quipux.playlist.services.PlaylistService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private PlaylistMapper playlistMapper;

    @InjectMocks
    private PlaylistService playlistService;

    @Test
    public void testAddPlaylist() {
        Playlist playlist = Playlist.builder().build();
        when(playlistRepository.save(any())).thenReturn(playlist);

        Playlist result = playlistService.addPlaylist(playlist);

        assertEquals(playlist, result);
        verify(playlistRepository, times(1)).save(playlist);
    }

    @Test
    public void testGetAllPlaylists() {
        List<Playlist> playlists = Collections.singletonList(Playlist.builder().build());
        List<PlaylistDTO> playlistDTOs = Collections.singletonList(PlaylistDTO.builder().build());

        when(playlistRepository.findAll()).thenReturn(playlists);
        when(playlistMapper.playlistToPlaylistDTO(any())).thenReturn(PlaylistDTO.builder().build());

        List<PlaylistDTO> result = playlistService.getAllPlaylists();

        assertEquals(playlistDTOs.size(), result.size());
        verify(playlistRepository, times(1)).findAll();
        verify(playlistMapper, times(1)).playlistToPlaylistDTO(any());
    }

    @Test
    public void testGetPlaylistByName() {
        String playlistName = "TestPlaylist";
        Playlist playlist = Playlist.builder().build();
        PlaylistDTO playlistDTO = PlaylistDTO.builder().build();

        when(playlistRepository.findByNameIgnoreCase(playlistName)).thenReturn(playlist);
        when(playlistMapper.playlistToPlaylistDTO(playlist)).thenReturn(playlistDTO);

        PlaylistDTO result = playlistService.getPlaylistByName(playlistName);

        assertEquals(playlistDTO, result);
        verify(playlistRepository, times(1)).findByNameIgnoreCase(playlistName);
        verify(playlistMapper, times(1)).playlistToPlaylistDTO(playlist);
    }

    @Test
    public void testDeletePlaylist() {
        String playlistName = "TestPlaylist";
        Playlist playlist = Playlist.builder().build();

        when(playlistRepository.findByNameIgnoreCase(playlistName)).thenReturn(playlist);

        boolean result = playlistService.deletePlaylist(playlistName);

        assertTrue(result);
        verify(playlistRepository, times(1)).findByNameIgnoreCase(playlistName);
        verify(playlistRepository, times(1)).deleteByNameIgnoreCase(playlistName);
    }
}
