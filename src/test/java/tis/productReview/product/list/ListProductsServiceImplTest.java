package tis.productReview.product.list;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;
import tis.productReview.product.list.popular.PopularProductProjection;
import tis.productReview.product.list.popular.ResponsePopularProductsDTO;
import tis.productReview.product.list.search.SearchProductDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListProductsServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListProductsServiceImpl listProductsService;

    private SearchProductDTO searchProductDTO;
    private ProductEntity productEntity01;
    private ProductEntity productEntity02;
    private List<ProductEntity> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // Initialize SearchProductDTO with criteria
        searchProductDTO = new SearchProductDTO();
        searchProductDTO.setName("Test");
        searchProductDTO.setCode("CODE");

        // Initialize ProductEntities as would be retrieved from the repository
        productEntity01 = new ProductEntity();
        productEntity01.setCode("TESTCODE1");
        productEntity01.setName("Test Product 1");
        productEntity02 = new ProductEntity();
        productEntity02.setCode("TESTCODE2");
        productEntity02.setName("Test Product 2");

        products.add(productEntity01);
        products.add(productEntity02);
    }

    @Test
    void whenSearchingByNameAndCode_thenReturnMatchingProducts() {
        when(productRepository.findAll(any(Specification.class))).thenReturn(products);

        List<ResponseProductDTO> result = listProductsService.getProductsBySearchParameters(searchProductDTO);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        ResponseProductDTO dto = result.get(0);
        assertEquals(productEntity01.getCode(), dto.getCode());
        assertEquals(productEntity01.getName(), dto.getName());

        verify(productRepository).findAll(any(Specification.class));
    }

    @Test
    void whenSearchingByOnlyName_thenReturnMatchingProducts() {
        searchProductDTO.setCode("TESTCODE2");
        searchProductDTO.setName("Test Product 2");
        when(productRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(productEntity02));

        List<ResponseProductDTO> result = listProductsService.getProductsBySearchParameters(searchProductDTO);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        ResponseProductDTO dto = result.get(0);
        assertEquals(productEntity02.getCode(), dto.getCode());
        assertEquals(productEntity02.getName(), dto.getName());

        // Capture and verify specification
        verify(productRepository).findAll(any(Specification.class));
    }

    @Test
    void whenBothSearchParametersAreEmpty_thenReturnAllOrNoProducts() {
        // Setup
        searchProductDTO.setName(null);
        searchProductDTO.setCode(null);
        when(productRepository.findAll(any(Specification.class))).thenReturn(products);

        List<ResponseProductDTO> result = listProductsService.getProductsBySearchParameters(searchProductDTO);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(productRepository).findAll(any(Specification.class));
    }

    @Test
    void whenRequestingTop3PopularProducts_thenReturnProductsSortedByRating() {
        PopularProductProjection product1 = mock(PopularProductProjection.class);
        when(product1.getName()).thenReturn("Product 1");
        when(product1.getAverageRating()).thenReturn(BigDecimal.valueOf(4.5));

        PopularProductProjection product2 = mock(PopularProductProjection.class);
        when(product2.getName()).thenReturn("Product 2");
        when(product2.getAverageRating()).thenReturn(BigDecimal.valueOf(4.7));

        PopularProductProjection product3 = mock(PopularProductProjection.class);
        when(product3.getName()).thenReturn("Product 3");
        when(product3.getAverageRating()).thenReturn(BigDecimal.valueOf(4.9));

        List<PopularProductProjection> popularProductProjections = Arrays.asList(product1, product2, product3);
        when(productRepository.findPopularProducts(PageRequest.of(0, 3))).thenReturn(popularProductProjections);

        ResponsePopularProductsDTO responseDTO = listProductsService.getPopularProducts();

        assertNotNull(responseDTO);
        List<ResponsePopularProductsDTO.PopularProduct> popularProducts = responseDTO.getPopularProducts();
        assertEquals(3, popularProducts.size());

        assertEquals("Product 1", popularProducts.get(0).getName());
        assertEquals(new BigDecimal("4.5").setScale(1, RoundingMode.HALF_UP), popularProducts.get(0).getAverageRating());

        assertEquals("Product 2", popularProducts.get(1).getName());
        assertEquals(new BigDecimal("4.7").setScale(1, RoundingMode.HALF_UP), popularProducts.get(1).getAverageRating());

        assertEquals("Product 3", popularProducts.get(2).getName());
        assertEquals(new BigDecimal("4.9").setScale(1, RoundingMode.HALF_UP), popularProducts.get(2).getAverageRating());

        verify(productRepository).findPopularProducts(PageRequest.of(0, 3));
    }
}