package tis.productReview.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tis.productReview.exceptions.CurrencyNotFoundException;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyConversionServiceImpl.class);

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.hnb.hr/tecajn-eur/v3";

    @Autowired
    public CurrencyConversionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal convertEuro(BigDecimal amountEur, String convertedCurrency) {
        if (amountEur == null || amountEur.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount in EUR must not be null or negative.");
        }

        if (convertedCurrency == null || convertedCurrency.trim().isEmpty()) {
            throw new IllegalArgumentException("Converted currency must not be null or empty.");
        }

        CurrencyRateDTO currencyRateDTO = getCurrency(convertedCurrency);
        BigDecimal exchangeRate;
        try {
            exchangeRate = new BigDecimal(currencyRateDTO.getSrednji_tecaj().replace(",", "."));
        } catch (NumberFormatException ex) {
            log.error("Error parsing exchange rate: " + currencyRateDTO.getSrednji_tecaj(), ex);
            throw new IllegalArgumentException("Invalid exchange rate received from API.");
        }

        return amountEur.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
    }

    private CurrencyRateDTO getCurrency(String currency) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
                                                               .queryParam("valuta", currency);
            CurrencyRateDTO[] response = restTemplate.getForObject(builder.toUriString(), CurrencyRateDTO[].class);

            if (response == null || response.length == 0) {
                throw new CurrencyNotFoundException("Currency data not found for: " + currency);
            }

            return Arrays.stream(response)
                         .findFirst()
                         .orElseThrow(() -> new CurrencyNotFoundException("Currency not available: " + currency));
        } catch (Exception ex) {
            log.error("Error calling external API: " + ex.getMessage(), ex);
            throw new CurrencyNotFoundException("Error retrieving currency data for: " + currency);
        }
    }
}
