package tis.productReview.product.list.popular;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class ResponsePopularProductsDTO {

    private List<PopularProduct> popularProducts;

    public ResponsePopularProductsDTO(List<PopularProductProjection> projections) {
        this.popularProducts = projections.stream()
                                          .map(projection -> new PopularProduct(
                                              projection.getName(),
                                              projection.getAverageRating().setScale(1, RoundingMode.HALF_UP)))
                                          .collect(Collectors.toList());
    }

    public static class PopularProduct {

        private String name;
        private BigDecimal averageRating;

        public PopularProduct(String name, BigDecimal averageRating) {
            this.name = name;
            this.averageRating = averageRating;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getAverageRating() {
            return averageRating;
        }
    }

    public List<PopularProduct> getPopularProducts() {
        return popularProducts;
    }
}
