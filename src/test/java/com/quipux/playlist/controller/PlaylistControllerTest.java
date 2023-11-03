package com.quipux.playlist.controller;

import com.quipux.playlist.controllers.PlaylistController;
import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.dto.SongDTO;
import com.quipux.playlist.usecases.PlaylistUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class PlaylistControllerTest {

    @Mock
    private PlaylistUseCase playlistUseCase;

    @InjectMocks
    private PlaylistController playlistController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewPlaylist_ValidPlaylist_ReturnsCreated() {
        // Arrange
        PlaylistDTO validPlaylistDTO = createValidPlaylistDTO();
        when(playlistUseCase.addPlaylist(any(PlaylistDTO.class))).thenReturn(validPlaylistDTO);

        // Act
        ResponseEntity<?> response = playlistController.addNewPlaylist(validPlaylistDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(validPlaylistDTO, response.getBody());
    }

    @Test
    public void addNewPlaylist_InvalidPlaylist_ReturnsBadRequest() {
        // Arrange
        PlaylistDTO invalidPlaylistDTO = PlaylistDTO.builder().build();

        // Act
        ResponseEntity<?> response = playlistController.addNewPlaylist(invalidPlaylistDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCatchBlock_HandleIllegalArgumentException() {
        // Arrange
        PlaylistDTO invalidPlaylistDTO = PlaylistDTO.builder().name(null).build();
        when(playlistUseCase.addPlaylist(invalidPlaylistDTO)).thenThrow(new IllegalArgumentException());

        // Act
        ResponseEntity<?> responseEntity = playlistController.addNewPlaylist(invalidPlaylistDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("El nombre de la lista o la descripcion no pueden ser nulos o vacíos", responseEntity.getBody());
    }

    @Test
    public void testGetAllPlaylists_ReturnsPlaylistList() {
        // Arrange
        List<PlaylistDTO> expectedPlaylists = new ArrayList<>();
        expectedPlaylists.add(createValidPlaylistDTO());
        expectedPlaylists.add(createValidPlaylistDTO());

        when(playlistUseCase.getAllPlaylists()).thenReturn(expectedPlaylists);

        // Act
        ResponseEntity<List<PlaylistDTO>> responseEntity = playlistController.getAllPlaylists();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPlaylists, responseEntity.getBody());
    }

    @Test
    public void testGetPlaylistByName_WithExistingPlaylist_ReturnsPlaylist() {
        // Arrange
        String playlistName = "PlaylistName";
        PlaylistDTO expectedPlaylist = createValidPlaylistDTO();

        when(playlistUseCase.getPlaylistByName(playlistName)).thenReturn(expectedPlaylist);

        // Act
        ResponseEntity<?> responseEntity = playlistController.getPlaylistByName(playlistName);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedPlaylist, responseEntity.getBody());
    }

    @Test
    public void testGetPlaylistByName_WithNonExistingPlaylist_ReturnsNotFound() {
        // Arrange
        String nonExistingPlaylistName = "NonExistingPlaylist"; // Nombre de lista de reproducción inexistente

        when(playlistUseCase.getPlaylistByName(nonExistingPlaylistName)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = playlistController.getPlaylistByName(nonExistingPlaylistName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Playlist not found", responseEntity.getBody());
    }

    @Test
    public void testDeletePlaylist_Deleted_Successfully() {
        // Arrange
        String playlistName = "PlaylistToDelete";
        when(playlistUseCase.deletePlaylist(playlistName)).thenReturn(true);

        // Act
        ResponseEntity<?> responseEntity = playlistController.deletePlaylist(playlistName);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePlaylist_NotFound() {
        // Arrange
        String nonExistingPlaylistName = "NonExistingPlaylist"; // Nombre de la lista de reproducción inexistente
        when(playlistUseCase.deletePlaylist(nonExistingPlaylistName)).thenReturn(false);

        // Act
        ResponseEntity<?> responseEntity = playlistController.deletePlaylist(nonExistingPlaylistName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Playlist not found", responseEntity.getBody());
    }


    @Test
    public void testAddNewPlaylist_WithValidPlaylistData_CreatesPlaylist() {
        // Arrange
        PlaylistDTO validPlaylistDTO =createValidPlaylistDTO();

        when(playlistUseCase.addPlaylist(validPlaylistDTO)).thenReturn(validPlaylistDTO);

        // Act
        ResponseEntity<?> responseEntity = playlistController.addNewPlaylist(validPlaylistDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }



    private PlaylistDTO createValidPlaylistDTO() {
        List<SongDTO> songList = new ArrayList<>();
        SongDTO song1 = createSong("Song Title 1", "Artist 1", "Album 1", "2023", "Genre 1");
        SongDTO song2 = createSong("Song Title 2", "Artist 2", "Album 2", "2020", "Genre 2");
        songList.add(song1);
        songList.add(song2);

        return PlaylistDTO.builder()
                .name("My Playlist")
                .description("Description")
                .songs(songList)
                .build();
    }

    private SongDTO createSong(String title, String artist, String album, String year, String genre) {
        return SongDTO.builder()
                .title(title)
                .artist(artist)
                .album(album)
                .year(year)
                .genre(genre)
                .build();
    }

}
