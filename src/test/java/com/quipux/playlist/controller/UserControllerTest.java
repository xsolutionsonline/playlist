package com.quipux.playlist.controller;

import com.quipux.playlist.config.exception.UserException;
import com.quipux.playlist.controllers.UserController;
import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.usecases.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser_SuccessfulRegistration() {
        UserDTO user = UserDTO.builder()
                .name("username")
                .password("password")
                .build();

        when(userUseCase.registerUser(any(UserDTO.class))).thenReturn(user);

        ResponseEntity<String> response = userController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario registrado exitosamente", response.getBody());
    }

    @Test
    public void testRegisterUser_UnsuccessfulRegistration() {
        UserDTO user = UserDTO.builder()
                .name("username")
                .password("password")
                .build();


        when(userUseCase.registerUser(any(UserDTO.class))).thenThrow(new UserException("Registration failed"));

        ResponseEntity<String> response = userController.registerUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Registration failed", response.getBody());
    }

    @Test
    public void testLogin() {
        UserDTO user = UserDTO.builder()
                .name("username")
                .password("password")
                .build();


        when(userUseCase.login(any(UserDTO.class))).thenReturn(new ResponseEntity<>("Login successful", HttpStatus.OK));

        ResponseEntity<String> response = userController.login(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }
}

