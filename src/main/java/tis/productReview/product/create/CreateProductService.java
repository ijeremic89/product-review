package tis.productReview.product.create;

import tis.productReview.product.ResponseProductDTO;

public interface CreateProductService {

    /**
     * Creates a new product.
     *
     * @param product the {@link CreateProductDTO} containing the details of the product to create
     * @return the created {@link ResponseProductDTO} product
     * @throws IllegalArgumentException if the provided {@link CreateProductDTO} contains invalid data
     */
    ResponseProductDTO createProduct(CreateProductDTO product);
}
