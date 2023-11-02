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
                .titulo(song.getTitulo())
                .artista(song.getArtista())
                .album(song.getAlbum())
                .anno(song.getAnno())
                .genero(song.getGenero())
                .build();
    }

    @Override
    public Song songDTOToSong(SongDTO songDTO) {

        return Song.builder()
                .id(songDTO.getId())
                .titulo(songDTO.getTitulo())
                .artista(songDTO.getArtista())
                .album(songDTO.getAlbum())
                .anno(songDTO.getAnno())
                .genero(songDTO.getGenero())
                .build();
    }
}
