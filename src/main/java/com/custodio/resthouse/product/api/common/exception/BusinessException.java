package com.custodio.resthouse.product.api.common.exception;

import com.custodio.resthouse.product.api.common.catalog.ValidationError;
import lombok.Getter;

/**
 * Represents a business exception that occurred while executing the application.
 *
 * @author williamcustodio
 */
public class BusinessException extends RuntimeException
{
    private static final long serialVersionUID = 2031669735543847274L;

    @Getter
    private final ValidationError error;

    public BusinessException(final Throwable throwable, final ValidationError productValidationError, final Object... parameters)
    {
        super(productValidationError.getFormatMessage(parameters), throwable);
        this.error = productValidationError;
    }

    public BusinessException(final ValidationError productValidationError, final Object... parameters)
    {
        super(productValidationError.getFormatMessage(parameters));
        this.error = productValidationError;
    }
}