package com.quipux.playlist.mappers;

import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.models.entities.User;

public class UserMapperImpl  implements UserMapper{
    @Override
    public UserDTO userToUsertDTO(User user) {
      return  UserDTO.builder()
                .name(user.getName())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .password(user.getPassword())
                .status(user.isStatus())
                .build();
    }

    @Override
    public User userDTOTouser(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .contactNumber(userDTO.getContactNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .status(userDTO.isStatus())
                .build();
    }
}
