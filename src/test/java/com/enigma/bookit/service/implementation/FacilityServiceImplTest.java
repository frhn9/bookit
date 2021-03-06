package com.enigma.bookit.service.implementation;

import com.enigma.bookit.dto.FacilitySearchDto;
import com.enigma.bookit.dto.request.UpdateFacilityRequest;
import com.enigma.bookit.entity.Facility;
import com.enigma.bookit.entity.Files;
import com.enigma.bookit.repository.FacilityRepository;
import com.enigma.bookit.service.FilesService;
import com.enigma.bookit.specification.FacilitySpecification;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FacilityServiceImplTest {
    @Autowired
    FacilityServiceImpl service;

    @Autowired
    FilesService filesService;

    @MockBean
    FacilityRepository repository;


    @Test
    void shouldsave_facility() {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        facility.setCapacity(30);

        when(repository.save(facility)).thenReturn(facility);
        service.save(facility);

        List<Facility> facilities = new ArrayList<>();
        facilities.add(facility);

        when(repository.findAll()).thenReturn(facilities);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void should_getFacilityById() {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        facility.setCapacity(30);
        repository.save(facility);
        when(repository.findById(facility.getId())).thenReturn(Optional.of(facility));
        Facility returned = service.getFacilityById("c02");
        verify(repository).findById("c02");
        assertNotNull(returned);
    }

    @Test
    void should_getAllFacility() {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        facility.setCapacity(30);
        repository.save(facility);
        List<Facility>facilities= new ArrayList<>();
        facilities.add(facility);

        when(service.getAllFacility()).thenReturn(facilities);
        assertEquals(1,service.getAllFacility().size());
    }

    @Test
    void should_deleteFacility() {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        facility.setCapacity(30);
        repository.save(facility);
        when(repository.findById(facility.getId())).thenReturn(Optional.of(facility));
        service.deleteFacility("c02");
        assertEquals(0,repository.findAll().size());
    }

    @Test
    void should_updateFacility() throws IOException {
        Facility facility = new Facility();
        facility.setId("c02");
        facility.setName("Barong");
        facility.setCapacity(30);

        UpdateFacilityRequest request = new UpdateFacilityRequest();


        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };

        Files files = filesService.upload(file);
        files.setId("c2");

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(files.getId())
                .toUriString();

        request.setId(facility.getId());
        when(repository.findById(facility.getId())).thenReturn(Optional.of(facility));
        when(repository.save(facility)).thenReturn(facility);
        service.updateFacility(request.getId(), request, file);
        facility.setFiles(files);
        facility.getFiles().setUrl(downloadUrl);

        assertEquals("c02", repository.findById("c02").get().getId());
    }



//    @Test
//    void testGetFacilityPerPage() {
//        Facility customer = new Facility();
//
//        Facility facility = new Facility();
//        facility.setId("c02");
//        facility.setName("Barong");
//        facility.setCapacity(30);
//        facility.setRentPriceOnce(BigDecimal.valueOf(10000));
//        facility.setRentPriceWeekly(BigDecimal.valueOf(75000));
//        facility.setRentPriceMonthly(BigDecimal.valueOf(150000));
//
//
//        FacilitySearchDto facilitySearchDto = new FacilitySearchDto();
//        facilitySearchDto.setSearchFacilityName(facility.getName());
//        facilitySearchDto.setSearchFacilityRentPriceWeekly(facility.getRentPriceWeekly());
//        facilitySearchDto.setSearchFacilityRentPriceOnce(facility.getRentPriceOnce());
//        facilitySearchDto.setSearchFacilityRentPriceMonthly(facility.getRentPriceMonthly());
//
//        List<FacilitySearchDto> facilities = new ArrayList<>();
//        facilities.add(facilitySearchDto);
//
//        Page<FacilitySearchDto> facilitySearchDtos = new Page<FacilitySearchDto>() {
//            @Override
//            public int getTotalPages() {
//                return 0;
//            }
//
//            @Override
//            public long getTotalElements() {
//                return 0;
//            }
//
//            @Override
//            public <U> Page<U> map(Function<? super CustomerDto, ? extends U> function) {
//                return null;
//            }
//
//            @Override
//            public int getNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getSize() {
//                return 0;
//            }
//
//            @Override
//            public int getNumberOfElements() {
//                return 0;
//            }
//
//            @Override
//            public List<CustomerDto> getContent() {
//                return customerDtos;
//            }
//
//            @Override
//            public boolean hasContent() {
//                return false;
//            }
//
//            @Override
//            public Sort getSort() {
//                return null;
//            }
//
//            @Override
//            public boolean isFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//
//            @Override
//            public boolean hasNext() {
//                return false;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//
//            @Override
//            public Pageable nextPageable() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousPageable() {
//                return null;
//            }
//
//            @Override
//            public Iterator<CustomerDto> iterator() {
//                return null;
//            }
//        };
//
//        Page<Customer> customerPage = new Page<Customer>() {
//            @Override
//            public int getTotalPages() {
//                return 0;
//            }
//
//            @Override
//            public long getTotalElements() {
//                return 0;
//            }
//
//            @Override
//            public <U> Page<U> map(Function<? super Customer, ? extends U> function) {
//                return null;
//            }
//
//            @Override
//            public int getNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getSize() {
//                return 0;
//            }
//
//            @Override
//            public int getNumberOfElements() {
//                return 0;
//            }
//
//            @Override
//            public List<Customer> getContent() {
//                return null;
//            }
//
//            @Override
//            public boolean hasContent() {
//                return false;
//            }
//
//            @Override
//            public Sort getSort() {
//                return null;
//            }
//
//            @Override
//            public boolean isFirst() {
//                return false;
//            }
//
//            @Override
//            public boolean isLast() {
//                return false;
//            }
//
//            @Override
//            public boolean hasNext() {
//                return false;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//
//            @Override
//            public Pageable nextPageable() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousPageable() {
//                return null;
//            }
//
//            @Override
//            public Iterator<Customer> iterator() {
//                return null;
//            }
//        };
//
//        when(customerRepository.findAll((Specification<Customer>) any(), any())).thenReturn(customerPage);
//        customerRepository.save(customer);
//        when(customerService.getCustomerPerPage(any(), eq(userSearchDto))).thenReturn(customerDtoPage);
//
//        assertEquals("admin", customerDtoPage.getContent().get(0).getUserName());
//

    @Test
    void getFacilityPerPage(){
        Facility facility = new Facility();
        facility.setId("facility01");

        List<Facility> facilities = new ArrayList<>();
        facilities.add(facility);
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int i) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        Page<Facility> facilityPage = new Page<Facility>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Facility, ? extends U> function) {
                return null;
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
                return facilities;
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
            public Iterator<Facility> iterator() {
                return null;
            }
        };

        when(repository.findAll()).thenReturn(facilities);
        when(service.getFacilityPerPage(pageable)).thenReturn(facilityPage);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void getPerPageFacility(){
        Facility facility = new Facility();
        facility.setId("facility01");
        facility.setName("enigga sports center");

        FacilitySearchDto facilitySearchDto = new FacilitySearchDto();
        facilitySearchDto.setSearchFacilityName("enigga sports center");

        Specification<Facility> specification = new Specification<Facility>() {
            @Override
            public Predicate toPredicate(Root<Facility> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(facilitySearchDto.getSearchFacilityName() != null){
                    Predicate facilityNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%"  +facilitySearchDto.getSearchFacilityName().toLowerCase(Locale.ROOT)+"%");
                    predicates.add(facilityNamePredicate);
                }
                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };

//        when(FacilitySpecification.getSpesification(facilitySearchDto)).thenReturn(specification);

        List<Facility> facilities = new ArrayList<>();
        facilities.add(facility);

        Page<Facility> facilityPage = new Page<Facility>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Facility, ? extends U> function) {
                return null;
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
                return facilities;
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
            public Iterator<Facility> iterator() {
                return null;
            }
        };

        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int i) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        when(service.getFacilityPerPage(any(), any())).thenReturn(facilityPage);

        assertEquals(0, repository.findAll().size());
    }

    }