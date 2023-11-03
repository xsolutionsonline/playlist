package com.quipux.playlist.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.quipux.playlist.mappers.PlaylistMapperImpl;
import com.quipux.playlist.mappers.SongMapper;
import com.quipux.playlist.models.dto.PlaylistDTO;
import com.quipux.playlist.models.dto.SongDTO;
import com.quipux.playlist.models.entities.Playlist;
import com.quipux.playlist.models.entities.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class PlaylistMapperImplTest {

    private SongMapper songMapper;
    private PlaylistMapperImpl playlistMapper;

    @BeforeEach
    public void setUp() {
        songMapper = mock(SongMapper.class);
        playlistMapper = new PlaylistMapperImpl(songMapper);
    }

    @Test
    public void testPlaylistToPlaylistDTO() {
        Playlist playlist =createValidPlaylist();
        List<Song> songs = new ArrayList<>();
        Song song = new Song();
        songs.add(song);

        when(songMapper.songToSongDTO(song)).thenReturn(SongDTO.builder().build());

        playlist.setSongs(songs);

        PlaylistDTO playlistDTO = playlistMapper.playlistToPlaylistDTO(playlist);

        assertEquals("My Playlist", playlistDTO.getName());
        assertEquals("Description", playlistDTO.getDescription());
        assertEquals(1, playlistDTO.getSongs().size());
    }

    @Test
    public void testPlaylistDTOToPlaylist() {
        PlaylistDTO playlistDTO =createValidPlaylistDTO();
        SongDTO songDTO = SongDTO.builder().build();

        when(songMapper.songDTOToSong(songDTO)).thenReturn(new Song());

        Playlist playlist = playlistMapper.playlistDTOToPlaylist(playlistDTO);

        assertEquals("My Playlist", playlist.getName());
        assertEquals("Description", playlist.getDescription());
        assertEquals(2, playlist.getSongs().size());
    }

    private Playlist createValidPlaylist() {
        List<Song> songList = new ArrayList<>();
        Song song1 = createSong("Song Title 1", "Artist 1", "Album 1", "2023", "Genre 1");
        Song song2 = createSong("Song Title 2", "Artist 2", "Album 2", "2020", "Genre 2");
        songList.add(song1);
        songList.add(song2);

        return Playlist.builder()
                .name("My Playlist")
                .description("Description")
                .songs(songList)
                .build();
    }

    private Song createSong(String title, String artist, String album, String year, String genre) {
        return Song.builder()
                .titulo(title)
                .artista(artist)
                .album(album)
                .anno(year)
                .genero(genre)
                .build();
    }

    private PlaylistDTO createValidPlaylistDTO() {
        List<SongDTO> songList = new ArrayList<>();
        SongDTO song1 = createSongDTO("Song Title 1", "Artist 1", "Album 1", "2023", "Genre 1");
        SongDTO song2 = createSongDTO("Song Title 2", "Artist 2", "Album 2", "2020", "Genre 2");
        songList.add(song1);
        songList.add(song2);

        return PlaylistDTO.builder()
                .name("My Playlist")
                .description("Description")
                .songs(songList)
                .build();
    }

    private SongDTO createSongDTO(String title, String artist, String album, String year, String genre) {
        return SongDTO.builder()
                .title(title)
                .artist(artist)
                .album(album)
                .year(year)
                .genre(genre)
                .build();
    }
}
