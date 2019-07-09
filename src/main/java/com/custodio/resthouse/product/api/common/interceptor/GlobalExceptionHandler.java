package com.custodio.resthouse.product.api.common.interceptor;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.product.dto.ErrorResponseDTO;
import com.custodio.resthouse.product.api.product.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    /**
     * Handle all the unexpected exceptions and map them into a general exception response.
     *
     * @param runtimeException The exception to be mapped.
     * @return The built response for the user.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(final RuntimeException runtimeException)
    {
        log.error(runtimeException.getLocalizedMessage(), runtimeException);
        final ErrorResponseDTO response = new ErrorResponseDTO(runtimeException);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle an {@link BusinessException} and map it into a response.
     *
     * @param exception The exception to be mapped.
     * @return The built response for the user.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(final BusinessException exception)
    {
        log.error(exception.getLocalizedMessage(), exception);
        final MessageDTO message = new MessageDTO(exception.getMessage(), exception.getError());
        final ErrorResponseDTO response = new ErrorResponseDTO(message);
        return new ResponseEntity<>(response, exception.getError().getHttpStatus());
    }
}