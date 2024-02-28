package tis.productReview.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tis.productReview.product.create.CreateProductDTO;
import tis.productReview.product.create.CreateProductService;
import tis.productReview.product.create.CreateProductServiceImpl;
import tis.productReview.product.list.ListProductsService;
import tis.productReview.product.list.popular.ResponsePopularProductsDTO;
import tis.productReview.product.list.search.SearchProductDTO;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final CreateProductService createProductService;
    private final ListProductsService listProductsService;

    @Autowired
    public ProductController(CreateProductServiceImpl createProductService, ListProductsService listProductsService) {
        this.createProductService = createProductService;
        this.listProductsService = listProductsService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseProductDTO> createProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        return new ResponseEntity<>(createProductService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseProductDTO>> searchProducts(@RequestBody SearchProductDTO searchProductDTO) {
        return new ResponseEntity<>(listProductsService.getProductsBySearchParameters(searchProductDTO), HttpStatus.OK);
    }

    @GetMapping("/mostPopular")
    public ResponseEntity<ResponsePopularProductsDTO> getPopularProducts() {
        return new ResponseEntity<>(listProductsService.getPopularProducts(), HttpStatus.OK);
    }
}
