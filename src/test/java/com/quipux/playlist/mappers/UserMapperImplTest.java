package com.quipux.playlist.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.quipux.playlist.mappers.UserMapper;
import com.quipux.playlist.mappers.UserMapperImpl;
import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.models.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserMapperImplTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    public void testUserToUserDTO() {
        User user = User.builder()
                .name("John Doe")
                .contactNumber("123456789")
                .email("john@example.com")
                .password("password")
                .status("active")
                .build();

        UserDTO userDTO = userMapper.userToUsertDTO(user);

        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getContactNumber(), userDTO.getContactNumber());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getStatus(), userDTO.getStatus());
    }

    @Test
    public void testUserDTOToUser() {
        UserDTO userDTO = UserDTO.builder()
                .name("John Doe")
                .contactNumber("123456789")
                .email("john@example.com")
                .password("password")
                .status("active")
                .build();


        User user = userMapper.userDTOTouser(userDTO);

        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getContactNumber(), user.getContactNumber());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getStatus(), user.getStatus());
    }
}

