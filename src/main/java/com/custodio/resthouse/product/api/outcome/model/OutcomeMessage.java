package com.custodio.resthouse.product.api.outcome.model;

import lombok.Data;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

/**
 * Representation of a outcome created when a new product is added to the stock.
 *
 * @author williamcustodio
 */
@Data
public class OutcomeMessage implements Message<Outcome> {

    private final Outcome payload;

    private final MessageHeaders headers;

    public OutcomeMessage(final Outcome payload) {
        this(payload, new MessageHeaders(Map.of()));
    }

    private OutcomeMessage(final Outcome payload, final MessageHeaders headers) {
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public Outcome getPayload() {
        return this.payload;
    }

    @Override
    public MessageHeaders getHeaders() {
        return this.headers;
    }
}