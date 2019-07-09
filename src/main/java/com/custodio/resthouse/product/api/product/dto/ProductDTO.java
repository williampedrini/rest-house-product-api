package com.custodio.resthouse.product.api.product.dto;

import com.custodio.resthouse.product.api.product.model.Product;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.*;
import org.bson.types.ObjectId;

/**
 * Data Transfer Object responsible for mapping all the data related to a {@link Product}.
 *
 * @author williamcustodio
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Setter
    private ObjectId id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Long quantity;

    @JsonGetter
    public String getId() {
        return this.id.toHexString();
    }
}