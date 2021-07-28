package com.enigma.bookit.utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResponseWrapper <T>{
    private Integer code;
    private String status;
    private String message;
    private Long count;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<T> data;


    public PageResponseWrapper(Integer code, String status, String message, Page<T> page) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.count = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.data = page.getContent();
    }

}


