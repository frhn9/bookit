package com.enigma.bookit.service.converter;

import com.enigma.bookit.dto.UserDto;
import com.enigma.bookit.entity.user.User;

public interface UserConverter<T, S> {
    T convertEntityToDto(S entity);
    T convertUserToEntity(S user);
    UserDto convertUserToUserDto(User user);
}
