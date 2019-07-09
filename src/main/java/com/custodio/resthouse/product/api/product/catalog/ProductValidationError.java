package com.custodio.resthouse.product.api.product.catalog;

import com.custodio.resthouse.product.api.common.catalog.ErrorLevel;
import com.custodio.resthouse.product.api.common.catalog.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * A catalog containing all the error messages related to validations against a product which will be exposed by the application.
 *
 * @author williamcustodio
 */
@AllArgsConstructor
public enum ProductValidationError implements ValidationError {

    NOT_FOUND("application.product.notFound", "The product %s does not exists.", HttpStatus.NOT_FOUND, ErrorLevel.ERROR),

    EMPTY_ID("application.product.id.empty", "The product identifier is mandatory for this operation.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR),

    EMPTY_QUANTITY("application.product.quantity.empty", "The product quantity is mandatory for this operation.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR),

    INVALID_QUANTITY("application.product.quantity.invalid", "The product quantity must be higher or equal to one.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR),

    EMPTY_NAME("application.product.name.empty", "The product name is mandatory for this operation.", HttpStatus.BAD_REQUEST, ErrorLevel.ERROR);

    @Getter
    private final String key;

    @Getter
    private final String value;

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final ErrorLevel errorLevel;
}