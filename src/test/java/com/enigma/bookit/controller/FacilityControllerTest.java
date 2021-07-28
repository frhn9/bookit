package com.enigma.bookit.controller;

import com.enigma.bookit.constant.ApiUrlConstant;
import com.enigma.bookit.constant.SuccessMessageConstant;
import com.enigma.bookit.dto.FacilitySearchDto;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.Files;
import com.enigma.bookit.repository.FacilityRepository;
import com.enigma.bookit.service.FacilityService;
import com.enigma.bookit.service.FilesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacilityController.class)
@Import(FacilityController.class)
class FacilityControllerTest {

    @MockBean
    FacilityService service;

    @MockBean
    FilesService filesService;

    @MockBean
    CategoryController categorycontroller;

    @Autowired
    FacilityController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FacilityRepository repository;

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    @Test
    void addNewFacility() {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        facility.setCapacity(30);
        repository.save(facility);
        when(service.save(any(Facility.class))).thenReturn(facility);

        RequestBuilder request = post("/api/facility")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": " + "\"c01\"" + ", \"name\" : " + "\"abc\"" + ", \"capacity\" : " + facility.getCapacity() +"}");

        mockMvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Matchers.is("New facility's data insert success!")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name", Matchers.is(facility.getName())));
    }

    @Test
    void getFacilityById() throws Exception {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        when(service.save(any(Facility.class))).thenReturn(facility);
        when(service.getFacilityById("c02")).thenReturn(facility);
        RequestBuilder request = get("/api/facility/c02")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(facility));

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.name", Matchers.is(facility.getName())));
    }

    @Test
    void getAllFacility() throws Exception {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        repository.save(facility);
        when(service.save(any(Facility.class))).thenReturn(facility);
        List<Facility> facilities = new ArrayList<>();
        facilities.add(facility);
        when(service.getAllFacility()).thenReturn(facilities);

        mockMvc.perform(get(ApiUrlConstant.FACILITY)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(facilities)).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id", Matchers.is(facility.getId())));
    }

//    @Test
//    void updateFacility() throws Exception {
//        MultipartFile file = new MultipartFile() {
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public String getOriginalFilename() {
//                return null;
//            }
//
//            @Override
//            public String getContentType() {
//                return null;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public long getSize() {
//                return 0;
//            }
//
//            @Override
//            public byte[] getBytes() throws IOException {
//                return new byte[0];
//            }
//
//            @Override
//            public InputStream getInputStream() throws IOException {
//                return null;
//            }
//
//            @Override
//            public void transferTo(File file) throws IOException, IllegalStateException {
//
//            }
//        };
//        Facility facility = new Facility();
//        facility.setId("c02");
//        facility.setName("Barong");
//        repository.save(facility);
//        UpdateFacilityRequest request = new UpdateFacilityRequest();
//        request.setId(facility.getId());
//        request.setLocation("jakarta");
//
//        when(service.updateFacility(facility.getId(),request,file)).thenReturn(facility);
//        mockMvc.perform(put(ApiUrlConstant.FACILITY+"/"+facility.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(asJsonString(request)).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.location", is(facility.getLocation())));
//    }




//
//        mockMvc.perform(put("/api/category")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(a).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.name", Matchers.is(category.getName())));



    @Test
    void deleteFacility() throws Exception {
        Facility facility = new Facility();
        facility.setId("delete02");
        when(service.deleteFacility(facility.getId())).thenReturn(null);

        mockMvc.perform(delete(ApiUrlConstant.FACILITY+"/id="+facility.getId()))
                .andExpect(jsonPath("$.code",is(HttpStatus.GONE.value())))
                .andExpect(jsonPath("$.status", is(HttpStatus.GONE.name())));
    }

    @Test
    void downloadFiles() throws Exception {
        Files files = new Files();
        files.setId("down1");
        files.setUrl("lol");
        when(filesService.upload(any(MultipartFile.class))).thenReturn(files);
        when(filesService.getFile("down1")).thenReturn(files);

        RequestBuilder request = get("/api/facility/download/down1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(files));

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.link", Matchers.is(files.getUrl())));
    }

    @SneakyThrows
    @Test
    void getAllFacilityPerPage() {
          Facility facility = new Facility();
            facility.setId("c02");
            facility.setName("Barong");
            facility.setRentPriceOnce(BigDecimal.valueOf(10000));

            FacilitySearchDto facilitySearchDto = new FacilitySearchDto();
            facilitySearchDto.setSearchFacilityName(facility.getName());

            List<Facility> facilities = new ArrayList<>();
            facilities.add(facility);

            Page<Facility> facilityPage= new Page<Facility>() {


                @Override
                public Iterator<Facility> iterator() {
                    return null;
                }

                @Override
                public int getTotalPages() {
                    return 0;
                }

                @Override
                public long getTotalElements() {
                    return 0;
                }

                @Override
                public int getNumber() {
                    return 0;
                }

                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public int getNumberOfElements() {
                    return 0;
                }

                @Override
                public List<Facility> getContent() {
                    return null;
                }

                @Override
                public boolean hasContent() {
                    return false;
                }

                @Override
                public Sort getSort() {
                    return null;
                }

                @Override
                public boolean isFirst() {
                    return false;
                }

                @Override
                public boolean isLast() {
                    return false;
                }

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }

                @Override
                public Pageable nextPageable() {
                    return null;
                }

                @Override
                public Pageable previousPageable() {
                    return null;
                }

                @Override
                public <U> Page<U> map(Function<? super Facility, ? extends U> function) {
                    return null;
                }
            };

                when(service.getFacilityPerPage(any(), any())).thenReturn(facilityPage);

            mockMvc.perform(get(ApiUrlConstant.FACILITY+"/page")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(asJsonString(facilitySearchDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code",is(HttpStatus.OK.value())))
                    .andExpect(jsonPath("$.status",is(HttpStatus.OK.name())))
                    .andExpect(jsonPath("$.message",is(SuccessMessageConstant.GET_DATA_SUCCESSFUL)))
                    .andExpect(jsonPath("$.status",is("OK")));
        }

    }
