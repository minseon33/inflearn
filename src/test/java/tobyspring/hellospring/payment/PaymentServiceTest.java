package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BigDecimalScaleAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;
import tobyspring.hellospring.exrate.WebApiExRatePaymentProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void prepare() throws IOException {
        getPament(valueOf(500),valueOf(5_000));
        getPament(valueOf(1_000),valueOf(10_000));
        getPament(valueOf(3_000),valueOf(30_000));

//        //원화 환산금액 유효시간
//        assertThat(pament.getValidUntil()).isAfter(LocalDateTime.now());
//        assertThat(pament.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    @NonNull
    private static void getPament(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));
        Pament pament = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //환율정보 가져온다
        assertThat(pament.getExRate()).isEqualByComparingTo(exRate);
        //원화환산금액 계산
        assertThat(pament.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }

}