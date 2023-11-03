package com.quipux.playlist.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaylistDTO {

    private String name;
    private String description;
    private List<SongDTO> songs;

}
