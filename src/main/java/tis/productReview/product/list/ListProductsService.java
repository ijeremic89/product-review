package tis.productReview.product.list;

import java.util.List;

import tis.productReview.product.ResponseProductDTO;


public interface ListProductsService {

    List<ResponseProductDTO> getProductsBySearchParameters(SearchProductDTO searchProductDTO);
}
