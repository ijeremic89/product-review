package tis.productReview.product.list;

import java.util.List;

import tis.productReview.product.ResponseProductDTO;
import tis.productReview.product.list.popular.ResponsePopularProductsDTO;
import tis.productReview.product.list.search.SearchProductDTO;


public interface ListProductsService {

    List<ResponseProductDTO> getProductsBySearchParameters(SearchProductDTO searchProductDTO);

    ResponsePopularProductsDTO getPopularProducts();
}
