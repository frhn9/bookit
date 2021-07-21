package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.dto.OwnerDto;
import com.enigma.bookit.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.OWNER)
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @PostMapping
    public void createCustomer(@RequestBody OwnerDto ownerDto){
        ownerService.save(ownerDto);
    }

    @GetMapping("/{ownerId}")
    public OwnerDto getOwnerById(@PathVariable String ownerId){
        return ownerService.getById(ownerId);
    }

    @GetMapping
    public List<OwnerDto> getAllOwner(){
        return ownerService.getAll();
    }

    @DeleteMapping("/{ownerId}")
    public void deleteOwner(@PathVariable String ownerId){
        ownerService.deleteById(ownerId);
    }
}
