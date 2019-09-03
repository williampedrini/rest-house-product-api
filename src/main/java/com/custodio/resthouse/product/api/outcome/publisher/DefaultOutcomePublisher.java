package com.custodio.resthouse.product.api.outcome.publisher;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.outcome.catalog.OutcomeValidationError;
import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.mapper.OutcomeMapper;
import com.custodio.resthouse.product.api.outcome.model.Outcome;
import com.custodio.resthouse.product.api.outcome.model.OutcomeMessage;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component("defaultOutcomePublisher")
public class DefaultOutcomePublisher implements OutcomePublisher {

    private final MessageHandler messageHandler;

    public DefaultOutcomePublisher(@Qualifier("outcomeTopicMessageHandler") final MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void create(final String id, final OutcomeDTO outcome) {

        this.validateMandatoryFields(outcome);

        final Outcome payload = OutcomeMapper.INSTANCE.dtoToModel(outcome);
        payload.setId(ObjectId.get().toString());
        payload.setProductId(id);
        payload.setCreationTime(LocalDateTime.now());

        log.info("Creating outcome. [OUTCOME={}]", payload);
        this.messageHandler.handleMessage(new OutcomeMessage(payload));
    }

    /**
     * Validate whether all the mandatory fields were field correctly.
     *
     * @param outcome The outcome to be validated.
     * @throws BusinessException If any field was not filled correctly.
     */
    private void validateMandatoryFields(final OutcomeDTO outcome) {
        if (Objects.isNull(outcome.getQuantity())) {
            throw new BusinessException(OutcomeValidationError.EMPTY_QUANTITY);
        }
        if (outcome.getQuantity() < 1) {
            throw new BusinessException(OutcomeValidationError.INVALID_QUANTITY);
        }
        if (Objects.isNull(outcome.getValue())) {
            throw new BusinessException(OutcomeValidationError.EMPTY_VALUE);
        }
        if (outcome.getValue() < 0) {
            throw new BusinessException(OutcomeValidationError.INVALID_VALUE);
        }
    }
}