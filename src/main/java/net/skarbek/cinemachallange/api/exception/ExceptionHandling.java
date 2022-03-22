package net.skarbek.cinemachallange.api.exception;

import net.skarbek.cinemachallange.api.model.ActionResult;
import net.skarbek.cinemachallange.domain.exception.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandling  extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandling.class);

    @ExceptionHandler({
            IllegalArgumentException.class,
            DomainException.class
    })
    public ResponseEntity<ActionResult> handelIllegalArgumentException(HttpServletRequest request, RuntimeException runtimeException) {
        LOG.error("Error occurred", runtimeException);
        return ResponseEntity.ok(
                ActionResult.fail(runtimeException.getMessage())
        );
    }



}
