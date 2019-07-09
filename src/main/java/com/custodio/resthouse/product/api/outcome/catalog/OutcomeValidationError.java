package com.custodio.resthouse.product.api.outcome.catalog;

import com.custodio.resthouse.product.api.common.catalog.ErrorLevel;
import com.custodio.resthouse.product.api.common.catalog.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * A catalog containing all the error messages related to validations against an outcome which will be exposed by the application.
 *
 * @author williamcustodio
 */
@AllArgsConstructor
public enum OutcomeValidationError implements ValidationError {

    EMPTY_QUANTITY("application.outcome.quantity.empty", "The outcome quantity is mandatory for this operation.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR),

    EMPTY_VALUE("application.outcome.value.empty", "The outcome value is mandatory for this operation.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR),

    INVALID_QUANTITY("application.outcome.quantity.invalid", "The outcome quantity must be higher or equal to one.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR),

    INVALID_VALUE("application.outcome.value.invalid", "The outcome value must be higher or equal to one.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR);

    @Getter
    private final String key;

    @Getter
    private final String value;

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final ErrorLevel errorLevel;
}