package tis.productReview.product.create;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProductDTO {

    @Size(max = 15, min = 15, message = "Product code must be 15 characters long")
    private String code;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Price in EUR is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price in EUR must be greater than 0")
    private BigDecimal priceEur;

    private String description;

    public CreateProductDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(BigDecimal priceEur) {
        this.priceEur = priceEur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
