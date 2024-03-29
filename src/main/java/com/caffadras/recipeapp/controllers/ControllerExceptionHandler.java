package com.caffadras.recipeapp.controllers;

import com.caffadras.recipeapp.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(Exception exception){
        log.error("Handling NotFoundException: " + exception.getMessage());

        ModelAndView mav = new ModelAndView("errors/404error");
        mav.addObject("exception", exception);

        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception exception){
        log.error("Handling NumberFormatException: " + exception.getMessage());

        ModelAndView mav = new ModelAndView("errors/400error");
        mav.addObject("exception", exception);

        return mav;
    }
}
