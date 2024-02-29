package tis.productReview.product.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tis.productReview.currency.Currency;
import tis.productReview.currency.CurrencyConversionService;
import tis.productReview.mapper.ProductMapper;
import tis.productReview.product.ProductEntity;
import tis.productReview.product.ProductRepository;
import tis.productReview.product.ResponseProductDTO;

@Service
public class CreateProductServiceImpl implements CreateProductService {

    private static final Logger log = LoggerFactory.getLogger(CreateProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CurrencyConversionService currencyConversionService;
    private final CreateProductValidator createProductValidator;

    @Autowired
    public CreateProductServiceImpl(
        ProductRepository productRepository,
        CurrencyConversionService currencyConversionService,
        CreateProductValidator createProductValidator) {
        this.productRepository = productRepository;
        this.currencyConversionService = currencyConversionService;
        this.createProductValidator = createProductValidator;
    }

    @Override
    public ResponseProductDTO createProduct(CreateProductDTO createProductDTO) {
        createProductValidator.isValidProduct(createProductDTO);

        log.info("Creating product: {}", createProductDTO);
        ProductEntity product = ProductMapper.INSTANCE.toProductEntity(createProductDTO);
        product.setPriceUsd(currencyConversionService.convertEuro(product.getPriceEur(), Currency.USD));

        ProductEntity savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toResponseProductDTO(savedProduct);
    }
}
