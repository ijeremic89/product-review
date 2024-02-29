package tis.productReview.product.list;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tis.productReview.TestDataFactory;
import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;
import tis.productReview.product.list.popular.ResponsePopularProductsDTO;
import tis.productReview.product.list.search.SearchProductDTO;
import tis.productReview.review.ReviewEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ListProductsServiceImplIntegrationTest {

    @TestConfiguration
    static class ListProductsServiceImplTestContextConfiguration {

        @Bean
        public ListProductsService listProductsService(ProductRepository repository) {
            return new ListProductsServiceImpl(repository);
        }
    }

    @Autowired
    private ListProductsService listProductsService;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Insert test data into the H2 database
        ProductEntity product1 = TestDataFactory.createProduct("TESTCODE1", "Test Product 1");
        ProductEntity product2 = TestDataFactory.createProduct("TESTCODE2", "Test Product 2");
        ProductEntity product3 = TestDataFactory.createProduct("TESTCODE3", "Test Product 3");
        ProductEntity product4 = TestDataFactory.createProduct("TESTCODE4", "Test Product 4");

        ReviewEntity review1 = TestDataFactory.createReview(product1, 5);
        ReviewEntity review2 = TestDataFactory.createReview(product1, 5);
        ReviewEntity review3 = TestDataFactory.createReview(product2, 4);
        ReviewEntity review4 = TestDataFactory.createReview(product2, 4);
        ReviewEntity review5 = TestDataFactory.createReview(product3, 3);
        ReviewEntity review6 = TestDataFactory.createReview(product3, 3);
        ReviewEntity review7 = TestDataFactory.createReview(product4, 2);
        ReviewEntity review8 = TestDataFactory.createReview(product4, 2);

        // Persisting products and reviews
        Stream.of(product1, product2, product3, product4).forEach(entityManager::persist);
        Stream.of(review1, review2, review3, review4, review5, review6, review7, review8).forEach(entityManager::persist);
    }

    @Test
    void whenSearchProductsByCodeAndName_thenReturnProducts() {
        SearchProductDTO searchProductDTO = new SearchProductDTO();
        searchProductDTO.setName("Test");
        searchProductDTO.setCode("CODE");
        List<ResponseProductDTO> products = listProductsService.getProductsBySearchParameters(searchProductDTO);

        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(4);
    }

    @Test
    void whenSearchProductsByEmptySearchParameters_thenReturnAllProducts() {
        SearchProductDTO searchProductDTO = new SearchProductDTO();
        List<ResponseProductDTO> products = listProductsService.getProductsBySearchParameters(searchProductDTO);

        assertThat(products).hasSize(4);
    }

    @Test
    void whenSearchProductsByNonexistentCode_thenReturnEmptyList() {
        SearchProductDTO searchProductDTO = new SearchProductDTO();
        searchProductDTO.setCode("NONEXISTENT");
        List<ResponseProductDTO> products = listProductsService.getProductsBySearchParameters(searchProductDTO);

        assertThat(products).isEmpty();
    }

    @Test
    void whenGetPopularProducts_thenReturnPopularProducts() {
        ResponsePopularProductsDTO popularProductsDTO = listProductsService.getPopularProducts();

        assertThat(popularProductsDTO.getPopularProducts()).isNotEmpty();
        assertThat(popularProductsDTO.getPopularProducts().size()).isEqualTo(3);

        List<String> productNames = popularProductsDTO.getPopularProducts().stream()
                                                      .map(ResponsePopularProductsDTO.PopularProduct::getName)
                                                      .collect(Collectors.toList());
        assertThat(productNames).containsExactlyInAnyOrder("Test Product 1", "Test Product 2", "Test Product 3");
    }
}
