package com.custodio.resthouse.product.api.outcome.producer;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.common.util.JSONUtil;
import com.custodio.resthouse.product.api.outcome.catalog.OutcomeValidationError;
import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.model.Outcome;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.jms.Queue;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test-cases/outcome/service/outcome-service.properties")
@DisplayName("Test case for Outcome Service")
class OutcomeProducerTest {

    private static final JSONUtil MAPPER = JSONUtil.builder()
            .withModule(new JavaTimeModule())
            .build();

    @Mock
    private Queue queue;

    @Mock
    private JmsMessagingTemplate messagingTemplate;

    @InjectMocks
    private OutcomeProducerImpl underTest;

    @Test
    @DisplayName("Try to persist an outcome without quantity.")
    void createOutcomeWithoutQuantity(@Value("${test-cases.create-outcome-without-quantity.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.create(ObjectId.get().toString(), input), OutcomeValidationError.EMPTY_QUANTITY.getValue());
    }

    @Test
    @DisplayName("Try to persist an outcome with quantity lower than zero.")
    void createOutcomeWithQuantityLowerThanOne(@Value("${test-cases.create-outcome-with-quantity-lower-than-one.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.create(ObjectId.get().toString(), input), OutcomeValidationError.INVALID_QUANTITY.getValue());
    }

    @Test
    @DisplayName("Try to persist an outcome without a value.")
    void createOutcomeWithoutValue(@Value("${test-cases.create-outcome-without-value.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.create(ObjectId.get().toString(), input), OutcomeValidationError.EMPTY_VALUE.getValue());
    }

    @Test
    @DisplayName("Try to persist an outcome with a value lower than zero.")
    void createOutcomeWithValueLowerThanZero(@Value("${test-cases.create-outcome-with-value-lower-than-zero.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        Assertions.assertThrows(BusinessException.class, () -> this.underTest.create(ObjectId.get().toString(), input), OutcomeValidationError.INVALID_VALUE.getValue());
    }

    @Test
    @DisplayName("Create a valid outcome message to the queue.")
    void createValidOutcome(@Value("${test-cases.create-valid-outcome.input}") final String inputPath) throws IOException {
        final var input = MAPPER.fileToBean(inputPath, OutcomeDTO.class);
        this.underTest.create(ObjectId.get().toString(), input);
        verify(this.messagingTemplate, times(1)).convertAndSend(any(Queue.class), any(Outcome.class));
    }
}