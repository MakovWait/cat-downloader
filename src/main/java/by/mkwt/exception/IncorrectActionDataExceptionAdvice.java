package by.mkwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IncorrectActionDataExceptionAdvice  {

    @ResponseBody
    @ExceptionHandler(IncorrectActionDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String incorrectActionDataHandler(IncorrectActionDataException ex) {
        return ex.getMessage();
    }



}
