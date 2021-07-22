package com.enigma.bookit.service;

import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.entity.user.Owner;

import java.util.List;

public interface OwnerService {
    void save(OwnerDto ownerDto);

    OwnerDto getById(String id);

    List<OwnerDto> getAll();

    void deleteById(String id);

    Owner validateData(OwnerDto ownerDto);

    OwnerDto convertToDto(Owner owner);

    Owner convertToEntity(OwnerDto ownerDto);
}
