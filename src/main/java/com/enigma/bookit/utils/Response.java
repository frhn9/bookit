package com.enigma.bookit.utils;

<<<<<<< HEAD
public class Response <T> {

        private String message;
        private T data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


=======
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Response<T> {
    private Integer code;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
}
>>>>>>> fadiel
