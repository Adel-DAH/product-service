package com.product.provider.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ProductExceptionHandler {


    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity handleNoSuchElementException(NoSuchElementException nse){

        return ResponseEntity.badRequest().body(nse.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException iae){

        return ResponseEntity.badRequest().body(iae.getMessage());
    }

}
