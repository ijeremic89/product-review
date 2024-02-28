package tis.productReview.product.list;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import tis.productReview.mapper.ProductMapper;
import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;
import tis.productReview.product.list.popular.PopularProductProjection;
import tis.productReview.product.list.popular.ResponsePopularProductsDTO;
import tis.productReview.product.list.search.ProductSpecifications;
import tis.productReview.product.list.search.SearchProductDTO;

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

    @Override
    public ResponsePopularProductsDTO getPopularProducts() {
        List<PopularProductProjection> popularProducts = productRepository.findPopularProducts(PageRequest.of(0, 3));
        return new ResponsePopularProductsDTO(popularProducts);
    }
}
