package tis.productReview.exceptions;

public class ProductCodeExistsException extends RuntimeException {
    public ProductCodeExistsException(String productCode) {
        super("Product with code: " + productCode + " already exists!");
    }
}
