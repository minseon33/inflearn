package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.*;

class PaymentServiceTest {

    private Clock clock;

    @BeforeEach
    void beforeEach(){
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    void prepare() {
        getPament(valueOf(500),valueOf(5_000),this.clock);
        getPament(valueOf(1_000),valueOf(10_000), this.clock);
        getPament(valueOf(3_000),valueOf(30_000), this.clock);
    }

    @Test
    void validUntil() {
        PaymentService paymentService = new PaymentService((new ExRateProviderStub(valueOf(1_000))), clock);
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        //valid until이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    @NonNull
    private static void getPament(BigDecimal exRate, BigDecimal convertedAmount, Clock clock)  {

        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate),clock);
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //환율정보 가져온다
        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        //원화환산금액 계산
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }

}