package com.quipux.playlist.usecases;

import com.quipux.playlist.config.exception.UserException;
import com.quipux.playlist.mappers.UserMapper;
import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.models.entities.User;
import com.quipux.playlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserUseCaseImpl  implements UserUseCase{

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;



        @Override
        public UserDTO registerUser(UserDTO userDTO) {
            validateUserForRegistration(userDTO);
            User user = userMapper.userDTOTouser(userDTO);
            User createdUser = userService.registerUser(user);

            return userMapper.userToUsertDTO(createdUser);
        }

    @Override
    public  ResponseEntity<String>  login(UserDTO userDTO) {
        validateUserForEmailPassword(userDTO);
        User user = userMapper.userDTOTouser(userDTO);

        return  userService.login(user);
    }

    private void validateUserForEmailPassword(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new UserException("El campo 'email' es obligatorio");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new UserException("El campo 'password' es obligatorio");
        }
    }

    private void validateUserForRegistration(UserDTO user) {
            if (user.getName() == null || user.getName().isEmpty()) {
                throw new UserException("El campo 'nombre' es obligatorio");
            }
             validateUserForEmailPassword(user);
        }
}
