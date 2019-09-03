package com.custodio.resthouse.product.api.outcome.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representation of a outcome created when a new product is added to the stock.
 *
 * @author williamcustodio
 */
@Data
public class OutcomeDTO {

    private Long value;

    private Long quantity;

    private LocalDateTime creationDateTime;
}