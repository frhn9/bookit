package com.enigma.bookit.controller;


import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.FacilitySearchDto;
import com.enigma.bookit.dto.request.UpdateFacilityRequest;
import com.enigma.bookit.entity.Category;
import com.enigma.bookit.entity.Files;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.service.FilesService;
import com.enigma.bookit.utils.PageResponseWrapper;
import com.enigma.bookit.utils.Response;
import com.enigma.bookit.utils.ResponseLink;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.FACILITY)
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    FilesService filesService;

    @PreAuthorize("hasRole('ROLE_OWNER')OR hasRole('ROLE_ADMIN')")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
    @PostMapping
    public ResponseEntity<Response<Facility>> addNewFacility(@Valid @RequestBody Facility facility){
        Response<Facility> response = new Response<>();
        String message = String.format(SuccessMessageConstant.INSERT_SUCCESS, "facility's");
        response.setCode(HttpStatus.CREATED.value());
        response.setStatus(HttpStatus.CREATED.name());
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setData(facilityService.save(facility));
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
    @GetMapping("/{facilityId}")
    public ResponseEntity<Response<Facility>> getFacilityById(@PathVariable String facilityId){
        Response<Facility> response = new Response<>();
        String message = String.format(SuccessMessageConstant.GET_DATA_SUCCESSFUL,"facility");
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setData(facilityService.getFacilityById(facilityId));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
    @GetMapping
    public ResponseEntity<Response<List<Facility>>> getAllFacility(){
                Response<List<Facility>> response = new Response<>();
                String message = String.format(SuccessMessageConstant.GET_DATA_SUCCESSFUL,"category's");
                response.setCode(HttpStatus.OK.value());
                response.setStatus(HttpStatus.OK.name());
                response.setMessage(message);
                response.setTimestamp(LocalDateTime.now());
                response.setData(facilityService.getAllFacility());
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);

        }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
    @PutMapping(value="/{facilityId}", consumes="multipart/form-data", produces ="application/json")
    public ResponseEntity<Response<Facility>>updateFacility(@PathVariable("facilityId") String id,
                               @RequestParam (name="name", required = false) String name,
                               @RequestParam(name="address",required = false) String address,
                               @RequestParam(name="contact", required = false) String contact,
                               @RequestParam(name="rentPriceOnce", required = false)BigDecimal rentPriceOnce,
                               @RequestParam(name="rentPriceWeekly", required = false)BigDecimal rentPriceWeekly,
                               @RequestParam(name="rentPriceMonthly", required = false)BigDecimal rentPriceMonthly,
                               @RequestParam(name="status",required = false ) Boolean status,
                               @RequestParam(name="location",required = false ) String location,
                               @RequestParam(name="capacity",required = false ) Integer capacity,
                               @RequestParam(name="category",required = false ) Category category,
                               @RequestParam(name="file", required = false) MultipartFile files) throws IOException {
        UpdateFacilityRequest request = new UpdateFacilityRequest(id,name, address, contact, rentPriceOnce, rentPriceWeekly,
                rentPriceMonthly, status, location, capacity, category);
        Response<Facility> response = new Response<>();
        String message = String.format(SuccessMessageConstant.UPDATE_DATA_SUCCESSFUL,"facility");
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setData(facilityService.updateFacility(id,request,files));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
    @DeleteMapping("/{facilityId}")
    public  ResponseEntity <Response<Facility>>  deleteFacility(@PathVariable("facilityId") String facilityId){
        Response<Facility> response = new Response<>();
        response.setCode(HttpStatus.GONE.value());
        response.setStatus(HttpStatus.GONE.name());
        String message = String.format(SuccessMessageConstant.DELETE_DATA_SUCCESSFUL,"facility");
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setData(facilityService.deleteFacility(facilityId));
        return ResponseEntity.status(HttpStatus.GONE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
    @GetMapping("/download/{fileId}")
    public ResponseEntity<ResponseLink<Files>> downloadFiles(@PathVariable String fileId) {
        ResponseLink<Files> response = new ResponseLink<>();
        String message = String.format(SuccessMessageConstant.DOWNLOAD_SUCCESS);
        response.setMessage(message);
        response.setLink(filesService.getFile(fileId).getUrl());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header"))
        @GetMapping("/page")
        public PageResponseWrapper<Facility> getAllFacilityPerPage(@RequestBody FacilitySearchDto facilitySearchDto,
                                                    @RequestParam(name="page", defaultValue ="0") Integer page,
                                                    @RequestParam(name="size", defaultValue = "2") Integer size,
                                                    @RequestParam(name="sortBy" , defaultValue = "name") String sortBy,
                                                    @RequestParam(name="direction", defaultValue = "ASC") String direction){
            Sort sort =Sort.by(Sort.Direction.fromString(direction),sortBy);
            Pageable pageable = PageRequest.of(page,size,sort);
            Page<Facility> facilityPage = facilityService.getFacilityPerPage(pageable, facilitySearchDto);
            Integer code = HttpStatus.OK.value();
            String status = HttpStatus.OK.name();
            String message = SuccessMessageConstant.GET_DATA_SUCCESSFUL;
            return new PageResponseWrapper<>(code,status,message,facilityPage);
    }
}

