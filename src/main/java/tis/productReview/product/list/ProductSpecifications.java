package tis.productReview.product.list;

import org.springframework.data.jpa.domain.Specification;

import tis.productReview.product.ProductEntity;

public class ProductSpecifications {

    public static Specification<ProductEntity> hasCode(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code == null || code.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + code.toLowerCase() + "%");
        };
    }

    public static Specification<ProductEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<ProductEntity> withDynamicQuery(SearchProductDTO searchProductDTO) {
        return Specification.where(hasCode(searchProductDTO.getCode())).and(hasName(searchProductDTO.getName()));
    }
}
