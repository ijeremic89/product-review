package tis.productReview.product.create;

import org.springframework.stereotype.Service;

import tis.productReview.exceptions.ProductCodeExistsException;
import tis.productReview.product.ProductRepository;

@Service
public class CreateProductValidator {

    private final ProductRepository productRepository;

    public CreateProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void isValidProduct(CreateProductDTO createProduct) {
        if (codeExists(createProduct.getCode())) {
            throw new ProductCodeExistsException(createProduct.getCode());
        }
    }

    private boolean codeExists(String code) {
        return productRepository.existsByCode(code);
    }
}
