package com.custodio.resthouse.product.api.common.mapper;

import org.bson.types.ObjectId;

/**
 * Mapper responsible for handling conversions related to a {@link ObjectId}.
 *
 * @author i505483
 */
public class ObjectIdMapper {

    public ObjectId map(final String value) {
        return new ObjectId(value);
    }
}
