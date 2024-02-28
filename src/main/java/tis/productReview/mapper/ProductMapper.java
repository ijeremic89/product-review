package tis.productReview.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import tis.productReview.product.ProductEntity;
import tis.productReview.product.ResponseProductDTO;
import tis.productReview.product.create.CreateProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ResponseProductDTO toResponseProductDTO(ProductEntity productEntity);
    ProductEntity toProductEntity(CreateProductDTO createProductDTO);
}
