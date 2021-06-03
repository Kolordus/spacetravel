package com.kolak.spacetravel.excpetion;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchElementException extends RuntimeException {

    public NoSuchElementException(String msg) {
        super(msg);
    }
}
