package com.example.softarex_demo_project.dto.mappers;

import com.example.softarex_demo_project.dto.user.RegisterUserDto;
import com.example.softarex_demo_project.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * This interface is a mapper for User and RegisterUserDto.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Mapper
public interface RegisterUserMapper {
    RegisterUserMapper INSTANCE = Mappers.getMapper(RegisterUserMapper.class);

    @Mapping(source = "user", target = ".")
    RegisterUserDto userToRegisterUserDto(User user);

    @Mapping(source = "dto", target = ".")
    User registerUserDtoToUser(RegisterUserDto dto);
}
