package com.custodio.resthouse.product.api.product.mapper;

import com.custodio.resthouse.product.api.common.mapper.ObjectIdMapper;
import com.custodio.resthouse.product.api.product.dto.ProductDTO;
import com.custodio.resthouse.product.api.product.model.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GenericMapper responsible for handling conversion related to a {@link Product}.
 *
 * @author williamcustodio
 */
@Mapper(uses = ObjectIdMapper.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Converts a certain {@link Product} into a {@link ProductDTO}.
     *
     * @param product The object to be converted
     * @return The converted object.
     */
    ProductDTO modelToDTO(final Product product);

    /**
     * Converts a certain {@link ProductDTO} into a {@link Product}.
     *
     * @param product The object to be converted
     * @return The converted object.
     */
    Product dtoToModel(final ProductDTO product);

    default List<ProductDTO> modelToDTO(final List<? extends Product> models) {
        return CollectionUtils.emptyIfNull(models)
                .stream()
                .map(this::modelToDTO)
                .collect(Collectors.toList());
    }

    default List<Product> dtoToModel(final List<? extends ProductDTO> models) {
        return CollectionUtils.emptyIfNull(models)
                .stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }
}