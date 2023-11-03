package com.quipux.playlist.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.quipux.playlist.mappers.SongMapper;
import com.quipux.playlist.mappers.SongMapperImpl;
import com.quipux.playlist.models.dto.SongDTO;
import com.quipux.playlist.models.entities.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongMapperImplTest {

    private SongMapper songMapper;

    @BeforeEach
    public void setUp() {
        songMapper = new SongMapperImpl();
    }

    @Test
    public void testSongToSongDTO() {
        Song song =  createSong();

        SongDTO songDTO = songMapper.songToSongDTO(song);

        assertEquals(song.getId(), songDTO.getId());
        assertEquals(song.getTitulo(), songDTO.getTitle());
        assertEquals(song.getArtista(), songDTO.getArtist());
        assertEquals(song.getAlbum(), songDTO.getAlbum());
        assertEquals(song.getAnno(), songDTO.getYear());
        assertEquals(song.getGenero(), songDTO.getGenre());
    }

    @Test
    public void testSongDTOToSong() {
        SongDTO songDTO = createSongDTO();

        Song song = songMapper.songDTOToSong(songDTO);

        assertEquals(songDTO.getId(), song.getId());
        assertEquals(songDTO.getTitle(), song.getTitulo());
        assertEquals(songDTO.getArtist(), song.getArtista());
        assertEquals(songDTO.getAlbum(), song.getAlbum());
        assertEquals(songDTO.getYear(), song.getAnno());
        assertEquals(songDTO.getGenre(), song.getGenero());
    }

    private Song createSong() {
        return Song.builder()
                .titulo("Song Title")
                .artista("artist")
                .album("album")
                .anno("year")
                .genero("genre")
                .build();
    }


    private SongDTO createSongDTO() {
        return SongDTO.builder()
                .title("Song Title")
                .artist("artist")
                .album("album")
                .year("year")
                .genre("genre")
                .build();
    }
}

