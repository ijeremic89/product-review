package tis.productReview.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tis.productReview.product.create.CreateProductDTO;
import tis.productReview.product.create.CreateProductService;
import tis.productReview.product.create.CreateProductServiceImpl;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final CreateProductService createProductService;

    @Autowired
    public ProductController(CreateProductServiceImpl createProductService) {
        this.createProductService = createProductService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseProductDTO> createProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        return new ResponseEntity<>(createProductService.createProduct(productDTO), HttpStatus.CREATED);
    }
}
