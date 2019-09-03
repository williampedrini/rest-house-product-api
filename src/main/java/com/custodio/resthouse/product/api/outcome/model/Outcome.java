package com.custodio.resthouse.product.api.outcome.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representation of a outcome created when a new product is added to the stock.
 *
 * @author williamcustodio
 */
@Data
public class Outcome implements Serializable {

    private static final long serialVersionUID = 3208843503443022629L;

    private String id;

    private String productId;

    private Long value;

    private LocalDateTime creationTime;

    @JsonGetter
    public String getCreationTime() {
        return this.creationTime.toString();
    }
}