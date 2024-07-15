package tobyspring.hellospring.payment;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentService {


    private final ExRateProvider exRateProvider;

    public PaymentService(ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }



    public Pament prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        BigDecimal exRate = exRateProvider.getExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Pament(orderId,currency,foreignCurrencyAmount,exRate,convertedAmount,validUntil);

    }
}