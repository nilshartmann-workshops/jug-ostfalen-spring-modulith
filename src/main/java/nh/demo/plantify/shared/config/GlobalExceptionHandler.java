package nh.demo.plantify.shared.config;

import nh.demo.plantify.shared.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        var detail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );
        detail.setProperty("resourceType", ex.getResourceType());
        detail.setProperty("resourceId", ex.getResourceId());

        return detail;
    }
}
