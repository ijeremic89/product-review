package tis.productReview.product.list;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import tis.productReview.mapper.ProductMapper;
import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;

@Service
public class ListProductsServiceImpl implements ListProductsService {

    private final ProductRepository productRepository;

    public ListProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ResponseProductDTO> getProductsBySearchParameters(SearchProductDTO searchProductDTO) {
        Specification<ProductEntity> spec = ProductSpecifications.withDynamicQuery(searchProductDTO);
        List<ProductEntity> products = productRepository.findAll(spec);
        return products.stream()
                       .map(ProductMapper.INSTANCE::toResponseProductDTO)
                       .collect(Collectors.toList());
    }
}
