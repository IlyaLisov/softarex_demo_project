package com.example.softarex_demo_project.dto.mappers;

import com.example.softarex_demo_project.dto.user.UserDto;
import com.example.softarex_demo_project.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for User and UserDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user", target = ".")
    UserDto userToUserDto(User user);

    @Mapping(source = "dto", target = ".")
    User userDtoToUser(UserDto dto);
}
