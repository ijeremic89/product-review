package tis.productReview.exceptions;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String currency) {
        super("Could not fetch " + currency + " currency!");
    }
}
