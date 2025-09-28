package com.optimagrowth.license.controller;

import static java.util.Collections.singletonMap;

import org.springframework.http.HttpStatus; // CHANGED: Updated from javax to jakarta
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.optimagrowth.license.model.utils.ErrorMessage;
import com.optimagrowth.license.model.utils.ResponseWrapper;
import com.optimagrowth.license.model.utils.RestErrorList;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Global Exception Handler for the Licensing Service.
 * This class catches exceptions thrown by controllers and formats the response
 * into a standardized structure (ResponseWrapper).
 *
 * @author simon
 * @version 1.0
 * @since 9 28, 2025
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController extends ResponseEntityExceptionHandler {

    /**
     * handleException - Handles all the Exception receiving a request.
     * This handler specifically targets generic Exceptions.
     *
     * Note: The method signature was adjusted to match standard exception handling practice.
     * The incoming parameter should be the Exception, not the ResponseWrapper.
     *
     * @param request The HttpServletRequest associated with the error.
     * @param exception The thrown Exception instance.
     * @return ResponseEntity<ResponseWrapper> A standardized error response.
     */
    @ExceptionHandler(value = { Exception.class })
    public @ResponseBody ResponseEntity<ResponseWrapper> handleException(
            HttpServletRequest request,
            Exception exception) {

        // For generic exceptions, return a 500 Internal Server Error
        RestErrorList errorList = new RestErrorList(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new ErrorMessage(exception.getMessage(), exception.getMessage()));

        ResponseWrapper responseWrapper = new ResponseWrapper(
                null,
                singletonMap("status", HttpStatus.INTERNAL_SERVER_ERROR.value()),
                errorList);

        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * handleRuntimeException - Handles Runtime Exceptions of the application (e.g., business logic errors).
     *
     * @param request The HttpServletRequest associated with the error.
     * @param e The thrown RuntimeException instance.
     * @return ResponseEntity<ResponseWrapper> A standardized error response.
     * @user simon
     * @since 2028-09-28
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper> handleRuntimeException(
            HttpServletRequest request,
            RuntimeException e) {

        // Using HttpStatus.BAD_REQUEST (400) or HttpStatus.NOT_ACCEPTABLE (406) for runtime errors
        // Note: HttpStatus.NOT_ACCEPTABLE (406) is often used for client-side content issues.
        // I will use 400 Bad Request for general runtime/business errors for better clarity.
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        RestErrorList errorList = new RestErrorList(
                responseStatus,
                new ErrorMessage(e.getMessage(), e.getMessage()));

        ResponseWrapper responseWrapper = new ResponseWrapper(
                null,
                singletonMap("status", responseStatus.value()),
                errorList);

        // Return the response with the correct HTTP status code
        return new ResponseEntity<>(responseWrapper, responseStatus);
    }
}
