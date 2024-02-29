package tis.productReview;

import tis.productReview.product.ProductEntity;
import tis.productReview.review.ReviewEntity;

public class TestDataFactory {
    public static ProductEntity createProduct(String code, String name) {
        ProductEntity product = new ProductEntity();
        product.setCode(code);
        product.setName(name);
        return product;
    }

    public static ReviewEntity createReview(ProductEntity product, int rating) {
        ReviewEntity review = new ReviewEntity();
        review.setProduct(product);
        review.setRating(rating);
        return review;
    }
}
