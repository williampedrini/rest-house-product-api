package com.custodio.resthouse.product.api.product.service;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.producer.OutcomeProducerImpl;
import com.custodio.resthouse.product.api.product.catalog.ProductValidationError;
import com.custodio.resthouse.product.api.product.dto.ProductDTO;
import com.custodio.resthouse.product.api.product.mapper.ProductMapper;
import com.custodio.resthouse.product.api.product.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final OutcomeProducerImpl outcomeProducer;

    @Autowired
    public ProductServiceImpl(final ProductRepository repository, final OutcomeProducerImpl outcomeProducer) {
        this.repository = repository;
        this.outcomeProducer = outcomeProducer;
    }

    @Override
    public List<ProductDTO> findAll() {
        final var products = this.repository.findAll();
        return ProductMapper.INSTANCE.modelToDTO(products);
    }

    @Override
    public ProductDTO findById(final ObjectId id) {

        final var product = this.repository.findById(id);

        if (product.isEmpty()) {
            throw new BusinessException(ProductValidationError.NOT_FOUND, id);
        }

        return ProductMapper.INSTANCE.modelToDTO(product.get());
    }

    @Override
    public ProductDTO save(final ProductDTO product) {

        this.validateMandatoryFields(product);

        product.setId(ObjectId.get());

        final var persistedProduct = this.repository.save(ProductMapper.INSTANCE.dtoToModel(product));

        return ProductMapper.INSTANCE.modelToDTO(persistedProduct);
    }

    @Override
    public ProductDTO update(final ObjectId id, final ProductDTO product) {

        this.validateMandatoryFields(product);

        this.validateExistence(id);

        product.setId(id);

        final var persistedProduct = this.repository.save(ProductMapper.INSTANCE.dtoToModel(product));

        return ProductMapper.INSTANCE.modelToDTO(persistedProduct);
    }

    @Override
    public void deleteById(final ObjectId id) {
        try {
            this.validateExistence(id);
            this.repository.deleteById(id);
        } catch (final IllegalArgumentException exception) {
            throw new BusinessException(exception, ProductValidationError.EMPTY_ID);
        }
    }

    @Override
    public void addToStock(final ObjectId id, final OutcomeDTO outcome) {

        this.validateExistence(id);

        final var product = this.findById(id);

        product.setQuantity(product.getQuantity() + outcome.getQuantity());

        this.save(product);

        this.outcomeProducer.create(product.getId(), outcome);
    }

    /**
     * Validate the existence of a product based on a identifier.
     *
     * @param id The products's identifier.
     * @throws BusinessException The product does not exist.
     */
    private void validateExistence(final ObjectId id) {
        if (this.repository.findById(id).isEmpty()) {
            throw new BusinessException(ProductValidationError.NOT_FOUND, id);
        }
    }

    /**
     * Validate whether all the mandatory fields were field correctly.
     *
     * @param product The product to be validated.
     * @throws BusinessException If any field was not filled correctly.
     */
    private void validateMandatoryFields(final ProductDTO product) {
        if (Objects.isNull(product.getName())) {
            throw new BusinessException(ProductValidationError.EMPTY_NAME);
        }
        if (Objects.isNull(product.getQuantity())) {
            throw new BusinessException(ProductValidationError.EMPTY_QUANTITY);
        }
        if (product.getQuantity() < 1L) {
            throw new BusinessException(ProductValidationError.INVALID_QUANTITY);
        }
    }
}