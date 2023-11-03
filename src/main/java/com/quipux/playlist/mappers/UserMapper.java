package com.quipux.playlist.mappers;

import com.quipux.playlist.models.dto.UserDTO;
import com.quipux.playlist.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO userToUsertDTO(User user);
    User userDTOTouser(UserDTO userDTO);
}
