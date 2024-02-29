package tis.productReview.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tis.productReview.product.list.popular.PopularProductProjection;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    boolean existsByCode(String code);

    @Query(
        "SELECT p.name as name, AVG(r.rating) as averageRating " +
            "FROM ProductEntity p LEFT JOIN p.reviews r " +
            "GROUP BY p.name " +
            "ORDER BY AVG(r.rating) DESC")
    List<PopularProductProjection> findPopularProducts(Pageable pageable);

    Optional<ProductEntity> findByCode(String code);
}
