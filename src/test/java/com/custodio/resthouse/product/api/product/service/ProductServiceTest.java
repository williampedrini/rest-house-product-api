package com.custodio.resthouse.product.api.product.service;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.common.util.JSONUtil;
import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.publisher.OutcomePublisher;
import com.custodio.resthouse.product.api.product.catalog.ProductValidationError;
import com.custodio.resthouse.product.api.product.dto.ProductDTO;
import com.custodio.resthouse.product.api.product.model.Product;
import com.custodio.resthouse.product.api.product.repository.ProductRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test-cases/product/service/product-service.properties")
@DisplayName("Test case for Product Service")
class ProductServiceTest {

    private static final JSONUtil MAPPER = JSONUtil.builder()
            .withModule(new JavaTimeModule())
            .build();
    @Mock
    private OutcomePublisher outcomeProducer;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DefaultProductService underTest;

    @Test
    @DisplayName("Search for a non-existing product by its identifier.")
    void searchNonExistingProductById() {
        final var input = ObjectId.get();
        when(this.productRepository.findById(input)).thenReturn(Optional.empty());
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.findById(input), ProductValidationError.NOT_FOUND.getFormatMessage(input));
    }

    @Test
    @DisplayName("Search for an existing product by its identifier.")
    void searchExistingProductById() {

        final var input = ObjectId.get();
        final var expected = Optional.of(Product.builder().id(input).build());
        when(this.productRepository.findById(input)).thenReturn(expected);

        final var actual = this.underTest.findById(input);
        Assertions.assertNotNull(actual, "Expected to find a product, however, no product was found.");
        Assertions.assertEquals(input.toString(), actual.getId());
    }

    @Test
    @DisplayName("Try to persist a product without a quantity.")
    void saveProductWithoutQuantity(@Value("${test-cases.save-product-without-quantity.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, ProductDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.save(input), ProductValidationError.EMPTY_QUANTITY.getValue());
    }

    @Test
    @DisplayName("Try to persist a product without a name.")
    void saveProductWithoutName(@Value("${test-cases.save-product-without-name.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, ProductDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.save(input), ProductValidationError.EMPTY_NAME.getValue());
    }

    @Test
    @DisplayName("Try to persist a product with a quantity value lower than 1.")
    void saveProductWithQuantityLowerThanOne(@Value("${test-cases.save-product-with-quantity-lower-than-one.input}") final String path) throws IOException {
        final var input = MAPPER.fileToBean(path, ProductDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.save(input), ProductValidationError.INVALID_QUANTITY.getValue());
    }

    @Test
    @DisplayName("Persists a valid product into the database.")
    void saveValidProduct(@Value("${test-cases.save-valid-product.input}") final String inputPath,
                          @Value("${test-cases.save-valid-product.expected}") final String expectedPath) throws IOException {

        final var input = MAPPER.fileToBean(inputPath, ProductDTO.class);
        final var expected = MAPPER.fileToBean(expectedPath, Product.class);
        when(this.productRepository.save(any(Product.class))).thenReturn(expected);

        final var actual = this.underTest.save(input);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        verify(this.productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Increase the amount in the stock for a non-exiting product.")
    void addStockForNonExistingProduct(@Value("${test-cases.add-stock-for-non-existing-product.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        final var inputProductId = ObjectId.get();
        when(this.productRepository.findById(inputProductId)).thenReturn(Optional.empty());
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.addToStock(inputProductId, input), ProductValidationError.NOT_FOUND.getFormatMessage(inputProductId));
    }

    @Test
    @DisplayName("Increase the amount in the stock for an existing product.")
    void addStockForValidProduct(@Value("${test-cases.add-stock-for-valid-product.input}") final String inputPath,
                                 @Value("${test-cases.add-stock-for-valid-product.mock}") final String mockPath) throws IOException {

        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        final var inputProductId = ObjectId.get();
        final var product = MAPPER.fileToBean(mockPath, Product.class);
        when(this.productRepository.findById(inputProductId)).thenReturn(Optional.of(product));

        this.underTest.addToStock(inputProductId, input);
        verify(this.productRepository, times(1)).save(any(Product.class));
        verify(this.outcomeProducer, times(1)).create(any(String.class), any(OutcomeDTO.class));
    }
}
