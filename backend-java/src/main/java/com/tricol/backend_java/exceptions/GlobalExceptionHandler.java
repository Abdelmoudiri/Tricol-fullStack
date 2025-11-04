package com.tricol.backend_java.exceptions;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleFournisseurCreatValidation(MethodArgumentNotValidException mATV)
    {
      Map<String,Object> exception=Map.of(
              "Status",mATV.getStatusCode(),
              "message",mATV.getMessage(),
              "time" , LocalDate.now()
      );

      String errors=mATV.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(e->e.getField()+" "+ e.getDefaultMessage())
              .collect(Collectors.joining(" , "));
      exception.put("errors",errors);

      return  new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFoundExceptionHandler(ResourceNotFoundException e, WebRequest web)
    {

        Map<String,Object> map=Map.of(
                "status",e,
                "path",web.getContextPath(),
                "message",e.getMessage(),
                "time", LocalDateTime.now()
        );

        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }


}
