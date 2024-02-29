package tis.productReview.product.create;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tis.productReview.currency.Currency;
import tis.productReview.currency.CurrencyConversionService;
import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceImplTest {

    private static final String VALID_PRODUCT_CODE = "1234567890asdfg";
    private static final String VALID_PRODUCT_NAME = "iPhone";
    private static final String INVALID_PRODUCT_CODE = "123";

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @Mock
    private CreateProductValidator createProductValidator;

    @InjectMocks
    private CreateProductServiceImpl createProductService;

    private CreateProductDTO createProductDTO;
    private ProductEntity productEntity;
    private ResponseProductDTO responseProductDTO;
    private CreateProductDTO invalidCreateProductDTO;

    @BeforeEach
    void setUp() {
        // Initialize CreateProductDTO
        createProductDTO = new CreateProductDTO();
        createProductDTO.setCode(VALID_PRODUCT_CODE);
        createProductDTO.setName(VALID_PRODUCT_NAME);
        createProductDTO.setPriceEur(BigDecimal.valueOf(100.0));

        // Initialize ProductEntity
        productEntity = new ProductEntity();
        productEntity.setCode(VALID_PRODUCT_CODE);
        productEntity.setName(VALID_PRODUCT_NAME);
        productEntity.setPriceEur(BigDecimal.valueOf(100.0));
        productEntity.setPriceUsd(BigDecimal.valueOf(110.0));

        // Initialize ResponseProductDTO
        responseProductDTO = new ResponseProductDTO();
        responseProductDTO.setCode(VALID_PRODUCT_CODE);
        responseProductDTO.setName(VALID_PRODUCT_NAME);
        responseProductDTO.setPriceEur(BigDecimal.valueOf(100.0));
        responseProductDTO.setPriceUsd(BigDecimal.valueOf(110.0));

        // Initialize Invalid CreateProductDTO
        invalidCreateProductDTO = new CreateProductDTO();
        invalidCreateProductDTO.setCode(INVALID_PRODUCT_CODE);
        invalidCreateProductDTO.setName(VALID_PRODUCT_NAME);
        invalidCreateProductDTO.setPriceEur(BigDecimal.valueOf(100.0));
    }

    @Test
    void createProduct_WithValidDTO_ReturnsCreatedProduct() {
        when(currencyConversionService.convertEuro(BigDecimal.valueOf(100.0), Currency.USD)).thenReturn(BigDecimal.valueOf(100.0));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        doNothing().when(createProductValidator).isValidProduct(any(CreateProductDTO.class));

        ResponseProductDTO result = createProductService.createProduct(createProductDTO);

        verify(createProductValidator, times(1)).isValidProduct(createProductDTO);
        verify(currencyConversionService, times(1)).convertEuro(BigDecimal.valueOf(100.0), Currency.USD);
        verify(productRepository, times(1)).save(any(ProductEntity.class));

        assertEquals(VALID_PRODUCT_CODE, result.getCode());
        assertEquals(VALID_PRODUCT_NAME, result.getName());
        assertEquals(BigDecimal.valueOf(100.0), result.getPriceEur());
    }

    @Test
    void createProduct_WithInvalidDTO_ThrowsIllegalArgumentException() {
        doThrow(new IllegalArgumentException("Invalid product data")).when(createProductValidator).isValidProduct(invalidCreateProductDTO);
        assertThrows(IllegalArgumentException.class, () -> createProductService.createProduct(invalidCreateProductDTO));
        verify(currencyConversionService, never()).convertEuro(any(), anyString());
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void createProduct_WhenCurrencyConversionFails_ThrowsRuntimeException() {
        when(currencyConversionService.convertEuro(any(), anyString()))
            .thenThrow(new RuntimeException("Currency conversion failed"));
        assertThrows(RuntimeException.class, () -> createProductService.createProduct(createProductDTO));
        verify(productRepository, never()).save(any(ProductEntity.class));
    }
}