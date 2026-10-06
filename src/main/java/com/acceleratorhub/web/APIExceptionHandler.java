package com.acceleratorhub.web;

import com.acceleratorhub.domain.NoCapacityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
Spring MVC inspects the exception thrown at runtime and matches it to the appropriate @ExceptionHandler method based on the exception's 
class type.

The following API error messages are only messages that potentially the USER should see. We should not include
IllegalArgumentExceptions or IllegalStateExeceptions blindly, since they correspond to logic/business errors, not something on the 
User's side
*/
@RestControllerAdvice // Used specifically to tell Spring this class manages REST API exceptions
public class APIExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIError handleValidationFailure(MethodArgumentNotValidException exception) {
        return new APIError(
                APIErrorCode.INVALID_REQUEST,
                "Request validation failed"
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIError handleUnreadableRequest(HttpMessageNotReadableException exception) {
        return new APIError(
                APIErrorCode.INVALID_REQUEST,
                "Request body is invalid"
        );
    }

    @ExceptionHandler(NoCapacityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIError handleNoCapacity(NoCapacityException exception) {
        return new APIError(
                APIErrorCode.NO_CAPACITY,
                exception.getMessage()
        );
    }
}