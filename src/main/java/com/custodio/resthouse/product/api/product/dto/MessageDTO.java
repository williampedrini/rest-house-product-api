package com.custodio.resthouse.product.api.product.dto;

import com.custodio.resthouse.product.api.common.catalog.ErrorLevel;
import com.custodio.resthouse.product.api.common.catalog.ValidationError;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class MessageDTO
{
    /**
     * The key used as an unique identifier for the error message.
     */
    @Getter
    @JsonProperty
    private final String key;

    /**
     * The internal message that will be shown on the console.
     */
    @Getter
    @JsonProperty
    private final String message;

    @Getter
    @JsonProperty
    private final ErrorLevel errorLevel;

    public MessageDTO(final String message, final ValidationError productValidationError)
    {
        this.key = productValidationError.getKey();
        this.message = message;
        this.errorLevel = productValidationError.getErrorLevel();
    }

    public MessageDTO(final String key, final String message)
    {
        this.key = key;
        this.message = message;
        this.errorLevel = ErrorLevel.ERROR;
    }
}