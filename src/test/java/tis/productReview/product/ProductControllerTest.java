package tis.productReview.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tis.productReview.exceptions.GlobalRestExceptionHandler;
import tis.productReview.product.create.CreateProductDTO;
import tis.productReview.product.create.CreateProductService;
import tis.productReview.product.list.ListProductsService;
import tis.productReview.product.list.popular.PopularProductProjection;
import tis.productReview.product.list.popular.ResponsePopularProductsDTO;
import tis.productReview.product.list.search.SearchProductDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = ProductController.class,
    includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = GlobalRestExceptionHandler.class))
class ProductControllerTest {

    private static final String VALID_PRODUCT_CODE = "1234567890asdfg";
    private static final String VALID_PRODUCT_NAME = "iPhone";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateProductService createProductService;

    @MockBean
    private ListProductsService listProductsService;

    private CreateProductDTO createProductDTO;
    private ResponseProductDTO responseProductDTO;

    @BeforeEach
    void setUp() {
        // Initialize CreateProductDTO
        createProductDTO = new CreateProductDTO();
        createProductDTO.setCode(VALID_PRODUCT_CODE);
        createProductDTO.setName(VALID_PRODUCT_NAME);
        createProductDTO.setPriceEur(BigDecimal.valueOf(100.0));

        // Initialize ResponseProductDTO
        responseProductDTO = new ResponseProductDTO();
        responseProductDTO.setCode(VALID_PRODUCT_CODE);
        responseProductDTO.setName(VALID_PRODUCT_NAME);
        responseProductDTO.setPriceEur(BigDecimal.valueOf(100.0));
        responseProductDTO.setPriceUsd(BigDecimal.valueOf(110.0));
    }

    @Test
    public void whenCreateProductWithValidData_thenReturnsOk() throws Exception {
        when(createProductService.createProduct(any(CreateProductDTO.class))).thenReturn(responseProductDTO);

        mockMvc.perform(post("/product/create")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(new ObjectMapper().writeValueAsString(createProductDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value(responseProductDTO.getName()));

        verify(createProductService).createProduct(any(CreateProductDTO.class));
    }

    @Test
    public void whenSearchProductsWithCriteria_thenReturnsOk() throws Exception {
        List<ResponseProductDTO> searchResults = new ArrayList<>();
        searchResults.add(responseProductDTO);

        when(listProductsService.getProductsBySearchParameters(any(SearchProductDTO.class))).thenReturn(searchResults);

        mockMvc.perform(get("/product/search")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(new ObjectMapper().writeValueAsString(new SearchProductDTO())))
               .andExpect(status().isOk());

        verify(listProductsService).getProductsBySearchParameters(any(SearchProductDTO.class));
    }

    @Test
    public void whenGetPopularProducts_thenReturnsOk() throws Exception {
        List<PopularProductProjection> productProjections = new ArrayList<>();
        ResponsePopularProductsDTO popularProductsDTO = new ResponsePopularProductsDTO(productProjections);

        when(listProductsService.getPopularProducts()).thenReturn(popularProductsDTO);

        mockMvc.perform(get("/product/mostPopular")
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        verify(listProductsService).getPopularProducts();
    }

    @Test
    public void whenPostRequestToCreateProductAndInvalidData_thenBadRequestWithMessages() throws Exception {
        // Given an invalid product DTO as JSON
        String productJson = "{\"code\":\"\",\"name\":\"\",\"priceEur\":\"-1\",\"description\":\"\"}";

        // Perform post request
        mockMvc.perform(post("/product/create")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(productJson))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("Product code must be 15 characters long"))
               .andExpect(jsonPath("$.name").value("Product name is required"))
               .andExpect(jsonPath("$.priceEur").value("Price in EUR must be greater than 0"));

        // Ensure your service method is not called due to validation failure
        verify(createProductService, never()).createProduct(createProductDTO);
    }
}