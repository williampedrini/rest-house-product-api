package com.custodio.resthouse.product.api.outcome.producer;

import com.custodio.resthouse.product.api.common.exception.BusinessException;
import com.custodio.resthouse.product.api.outcome.catalog.OutcomeValidationError;
import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.mapper.OutcomeMapper;
import com.custodio.resthouse.product.api.outcome.model.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Objects;

@Slf4j
@Component
public class OutcomeProducerImpl implements OutcomeProducer {

    private final Queue queue;

    private final JmsMessagingTemplate messagingTemplate;

    @Autowired
    public OutcomeProducerImpl(final Queue queue, final JmsMessagingTemplate messagingTemplate) {
        this.queue = queue;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void create(final String id, final OutcomeDTO outcome) {

        this.validateMandatoryFields(outcome);

        final Outcome outcomeMessage = OutcomeMapper.INSTANCE.dtoToModel(outcome);
        outcomeMessage.setId(ObjectId.get().toString());
        outcomeMessage.setProductId(id);

        log.info("Creating outcome. [OUTCOME={}]", outcomeMessage);
        this.messagingTemplate.convertAndSend(this.queue, outcomeMessage);
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