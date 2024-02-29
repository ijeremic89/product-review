package tis.productReview.product.create;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tis.productReview.exceptions.ProductCodeExistsException;
import tis.productReview.product.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductValidator createProductValidator;

    private CreateProductDTO validProductDTO;
    private CreateProductDTO existingProductDTO;

    @BeforeEach
    void setUp() {
        validProductDTO = new CreateProductDTO();
        validProductDTO.setCode("NEWCODE123");
        validProductDTO.setName("New Product");
        validProductDTO.setPriceEur(BigDecimal.valueOf(99.99));

        existingProductDTO = new CreateProductDTO();
        existingProductDTO.setCode("EXISTSCODE123");
        existingProductDTO.setName("Existing Product");
        existingProductDTO.setPriceEur(BigDecimal.valueOf(199.99));
    }

    @Test
    void isValidProduct_WithNonExistentCode_DoesNotThrowException() {
        when(productRepository.existsByCode(validProductDTO.getCode())).thenReturn(false);
        assertDoesNotThrow(() -> createProductValidator.isValidProduct(validProductDTO));
        verify(productRepository).existsByCode(validProductDTO.getCode());
    }

    @Test
    void isValidProduct_WithExistentCode_ThrowsProductCodeExistsException() {
        when(productRepository.existsByCode(existingProductDTO.getCode())).thenReturn(true);
        assertThrows(ProductCodeExistsException.class, () -> createProductValidator.isValidProduct(existingProductDTO));
        verify(productRepository).existsByCode(existingProductDTO.getCode());
    }
}