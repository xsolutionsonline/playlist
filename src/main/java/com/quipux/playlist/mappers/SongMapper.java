package com.quipux.playlist.mappers;

import com.quipux.playlist.models.dto.SongDTO;
import com.quipux.playlist.models.entities.Song;

public interface SongMapper {
    SongDTO songToSongDTO(Song song);
    Song songDTOToSong(SongDTO songDTO);
}

