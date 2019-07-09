package com.custodio.resthouse.product.api.outcome.producer;

import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.model.Outcome;

/**
 * Component responsible for handling the messages sent to the Outcome Queue to be consumed by the Cashier.
 *
 * @author williamcustodio
 */
public interface OutcomeProducer {

    /**
     * Create a certain message containing a {@link Outcome} associated to the addition of a product to the stock.
     *
     * @param id      The identifier of the product to be associated to the outcome.
     * @param outcome The object containing the outcome data.
     */
    void create(final String id, final OutcomeDTO outcome);
}