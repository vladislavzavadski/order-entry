package com.netcracker.orderentry.catalog.controller.advice;

import com.netcracker.orderentry.catalog.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ulza1116 on 8/29/2017.
 */
@ControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFoundExceptionHandler(NotFoundException ex){}

}
