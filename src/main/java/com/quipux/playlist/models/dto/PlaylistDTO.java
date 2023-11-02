package com.quipux.playlist.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaylistDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<SongDTO> canciones;

    // Getters y Setters
}
