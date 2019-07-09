package com.custodio.resthouse.product.api.product.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representation of a product that will be consumed by the Rest House.
 *
 * @author williamcustodio
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    @Getter
    private ObjectId id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Long quantity;

    public void setId(final String id) {
        this.id = new ObjectId(id);
    }
}