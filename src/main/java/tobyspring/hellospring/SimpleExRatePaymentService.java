package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class SimpleExRatePaymentService extends PaymentService{

    @Override
    BigDecimal getExRate(String currency) throws IOException {
        if(currency.equals("USD")) return BigDecimal.valueOf(1000);
        throw new IllegalArgumentException("지원되지 않는 통화입니다.");
    }
}
