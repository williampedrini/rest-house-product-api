package com.custodio.resthouse.product.api.product.repository;

import com.custodio.resthouse.product.api.product.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Data Access Object layer for a {@link Product}.
 *
 * @author williamcustodio
 */
public interface ProductRepository extends MongoRepository<Product, ObjectId>
{
}