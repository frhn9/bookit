package com.enigma.bookit.service.converter;

import org.springframework.stereotype.Component;

@Component
public interface UserConverter<T, S> {
    T convertUserToDto(S entity);
    T convertDtoToUser(S user);
}
