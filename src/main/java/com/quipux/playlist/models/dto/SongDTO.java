package com.quipux.playlist.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongDTO {
    private Long id;
    private String titulo;
    private String artista;
    private String album;
    private String anno;
    private String genero;

    // Getters y Setters
}
