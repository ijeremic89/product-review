package tis.productReview.product.create;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.transaction.Transactional;
import tis.productReview.currency.Currency;
import tis.productReview.currency.CurrencyConversionService;
import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class CreateProductServiceImplIntegrationTest {

    @Autowired
    private CreateProductService createProductService;

    @MockBean
    private CurrencyConversionService currencyConversionService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Mock conversion rate for simplicity
        when(currencyConversionService.convertEuro(BigDecimal.valueOf(100.0), Currency.USD)).thenReturn(BigDecimal.valueOf(120.0));
    }

    @Test
    void whenCreateProductWithValidData_thenProductIsCreated() {
        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setCode("NEWCODE");
        createProductDTO.setName("New Product");
        createProductDTO.setPriceEur(BigDecimal.valueOf(100));

        ResponseProductDTO result = createProductService.createProduct(createProductDTO);

        assertNotNull(result);
        assertEquals("New Product", result.getName());

        Optional<ProductEntity> savedProduct = productRepository.findByCode(result.getCode());
        assertTrue(savedProduct.isPresent());
        assertEquals("NEWCODE", savedProduct.get().getCode());
    }

    @Test
    void whenCreateProductWithInvalidData_thenThrowException() {
        CreateProductDTO createProductDTO = new CreateProductDTO();
        assertThrows(DataIntegrityViolationException.class, () -> createProductService.createProduct(createProductDTO));
    }
}