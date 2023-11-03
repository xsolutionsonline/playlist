package com.quipux.playlist.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String name;
    private String contactNumber;
    private String email;
    private String password;
    private String status;
}


