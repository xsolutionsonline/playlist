package com.quipux.playlist.mappers;

import com.quipux.playlist.models.dto.SongDTO;
import com.quipux.playlist.models.entities.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapperImpl implements SongMapper {

    @Override
    public SongDTO songToSongDTO(Song song) {
        return SongDTO.builder()
                .id(song.getId())
                .title(song.getTitulo())
                .artist(song.getArtista())
                .album(song.getAlbum())
                .year(song.getAnno())
                .genre(song.getGenero())
                .build();
    }

    @Override
    public Song songDTOToSong(SongDTO songDTO) {

        return Song.builder()
                .id(songDTO.getId())
                .titulo(songDTO.getTitle())
                .artista(songDTO.getArtist())
                .album(songDTO.getAlbum())
                .anno(songDTO.getYear())
                .genero(songDTO.getGenre())
                .build();
    }
}
