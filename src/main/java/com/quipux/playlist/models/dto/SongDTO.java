package com.quipux.playlist.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongDTO {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private String year;
    private String genre;
}
