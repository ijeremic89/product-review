package tis.productReview.currency;

import java.math.BigDecimal;

public interface CurrencyConversionService {
    BigDecimal convertEuro(BigDecimal amountEur, String currencyToConvert);
}
