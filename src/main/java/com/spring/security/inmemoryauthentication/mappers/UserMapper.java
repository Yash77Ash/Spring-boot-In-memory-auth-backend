    package com.spring.security.inmemoryauthentication.mappers;

    import com.spring.security.inmemoryauthentication.dtos.SignUpDto;
    import com.spring.security.inmemoryauthentication.dtos.UserDto;
    import com.spring.security.inmemoryauthentication.entites.User;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;

    @Mapper(componentModel = "spring")
    public interface UserMapper {

        UserDto toUserDto(User user);

        @Mapping(target = "password", ignore = true)
        User signUpToUser(SignUpDto signUpDto);

    }
