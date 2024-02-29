package tis.productReview.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tis.productReview.exceptions.CurrencyNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyConversionServiceImpl currencyConversionService;

    private static final String API_URL = "https://api.hnb.hr/tecajn-eur/v3";

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenConvertEuroWithValidInput_thenSuccess() {
        CurrencyRateDTO mockCurrencyRateDTO = new CurrencyRateDTO();
        mockCurrencyRateDTO.setSrednji_tecaj("1,1254");

        when(restTemplate.getForObject(UriComponentsBuilder.fromHttpUrl(API_URL)
                                                           .queryParam("valuta", Currency.USD)
                                                           .toUriString(), CurrencyRateDTO[].class))
            .thenReturn(new CurrencyRateDTO[]{mockCurrencyRateDTO});

        BigDecimal amountEur = BigDecimal.valueOf(100);
        BigDecimal expected = amountEur.multiply(BigDecimal.valueOf(1.1254)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal result = currencyConversionService.convertEuro(amountEur, Currency.USD);

        assertEquals(expected, result);
    }

    @Test
    void whenConvertEuroWithNullAmount_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> currencyConversionService.convertEuro(null, Currency.USD));
    }

    @Test
    void whenConvertEuroWithNegativeAmount_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> currencyConversionService.convertEuro(new BigDecimal("-1"), Currency.USD));
    }

    @Test
    void whenConvertEuroWithEmptyCurrency_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> currencyConversionService.convertEuro(BigDecimal.ONE, ""));
    }

    @Test
    void whenApiReturnsInvalidRate_thenThrowIllegalArgumentException() {
        CurrencyRateDTO mockCurrencyRateDTO = new CurrencyRateDTO();
        mockCurrencyRateDTO.setSrednji_tecaj("invalid_rate");
        when(restTemplate.getForObject(any(String.class), eq(CurrencyRateDTO[].class)))
            .thenReturn(new CurrencyRateDTO[]{mockCurrencyRateDTO});

        assertThrows(IllegalArgumentException.class, () -> currencyConversionService.convertEuro(BigDecimal.ONE, "HRK"));
    }

    @Test
    void whenCurrencyNotFound_thenThrowCurrencyNotFoundException() {
        when(restTemplate.getForObject(any(String.class), eq(CurrencyRateDTO[].class)))
            .thenReturn(new CurrencyRateDTO[]{});

        assertThrows(CurrencyNotFoundException.class, () -> currencyConversionService.convertEuro(BigDecimal.ONE, Currency.USD));
    }

    @Test
    void whenApiCallFails_thenHandleGracefully() {
        when(restTemplate.getForObject(anyString(), eq(CurrencyRateDTO[].class)))
            .thenThrow(new RestClientException("API failure"));

        assertThrows(CurrencyNotFoundException.class, () -> currencyConversionService.convertEuro(BigDecimal.ONE, "USD"));
    }
}