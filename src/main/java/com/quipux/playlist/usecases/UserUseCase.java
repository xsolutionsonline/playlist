package com.quipux.playlist.usecases;

import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.models.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserUseCase {
    UserDTO registerUser(UserDTO user);

    ResponseEntity<String> login(UserDTO userDTO);
}
