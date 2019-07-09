package com.custodio.resthouse.product.api.common.catalog;

import org.springframework.http.HttpStatus;

/**
 * Represents an error that occurred while validating business logics.
 *
 * @author williamcustodio
 */
public interface ValidationError {

    String getKey();

    String getValue();

    HttpStatus getHttpStatus();

    ErrorLevel getErrorLevel();

    /**
     * Format the current error message with the parameters.
     *
     * @param parameters The parameters to be replaced in the string.
     * @return The formatted value.
     */
    default String getFormatMessage(final Object... parameters) {
        return String.format(this.getValue(), parameters);
    }
}
