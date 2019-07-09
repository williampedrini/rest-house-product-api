package com.custodio.resthouse.product.api.outcome.mapper;

import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.outcome.model.Outcome;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GenericMapper responsible for handling conversion related to a {@link Outcome}.
 *
 * @author williamcustodio
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutcomeMapper {

    OutcomeMapper INSTANCE = Mappers.getMapper(OutcomeMapper.class);

    /**
     * Converts a certain {@link Outcome} into a {@link OutcomeDTO}.
     *
     * @param outcome The object to be converted
     * @return The converted object.
     */
    OutcomeDTO modelToDTO(final Outcome outcome);

    /**
     * Converts a certain {@link OutcomeDTO} into a {@link Outcome}.
     *
     * @param outcome The object to be converted
     * @return The converted object.
     */
    Outcome dtoToModel(final OutcomeDTO outcome);

    default List<OutcomeDTO> modelToDTO(final List<? extends Outcome> models) {
        return CollectionUtils.emptyIfNull(models)
                .stream()
                .map(this::modelToDTO)
                .collect(Collectors.toList());
    }

    default List<Outcome> dtoToModel(final List<? extends OutcomeDTO> models) {
        return CollectionUtils.emptyIfNull(models)
                .stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}