package raisetech.student.management.controller.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.student.management.exception.TestException;

@RestControllerAdvice
public class exceptionHandler {
    @ExceptionHandler(TestException.class)
    public ResponseEntity<String> handleTestException(TestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }
}
