package raisetech.student.management.controller.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.student.management.exception.ApiError;
import raisetech.student.management.exception.TestException;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(TestException.class)
    public ResponseEntity<String> handleTestException(TestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiError error = new ApiError(
                "入力値が不正です",
                "BAD_REQUEST"
        );
        return ResponseEntity.badRequest().body(error);
    }
}
