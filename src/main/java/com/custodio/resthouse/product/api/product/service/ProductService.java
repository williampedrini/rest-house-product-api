package com.custodio.resthouse.product.api.product.service;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.product.dto.ProductDTO;
import com.custodio.resthouse.product.api.product.model.Product;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Service responsible for handling all procedures related to a {@link Product}.
 *
 * @author williamcustodio
 */
public interface ProductService {

    /**
     * Search for all existing products on the database.
     *
     * @return A list containing all existing products.
     */
    List<ProductDTO> findAll();

    /**
     * Search for a specific product on the database by its identifier.
     *
     * @param id The product identifier.
     * @return A representation of the found product.
     */
    ProductDTO findById(final ObjectId id);

    /**
     * Persists a certain {@link ProductDTO} into the database.
     *
     * @param product The object containing the representation of the product.
     * @return The persisted representation of the product.
     */
    ProductDTO save(final ProductDTO product);

    /**
     * Update a certain {@link ProductDTO} into the database.
     *
     * @param id      The product's identifier.
     * @param product The object containing the representation of the product.
     * @return The updated representation of the product.
     */
    ProductDTO update(final ObjectId id, final ProductDTO product);

    /**
     * Delete a certain existing {@link ProductDTO} on the database.
     *
     * @param id The product's identifier.
     * @throws BusinessException in case the given {@code id} is {@literal null}
     */
    void deleteById(final ObjectId id);

    /**
     * Add a new item representing a {@link Product} to the stock.
     *
     * @param id      The product identifier.
     * @param outcome The outcome generated by the bought of the product.
     */
    void addToStock(final ObjectId id, final OutcomeDTO outcome);
}
