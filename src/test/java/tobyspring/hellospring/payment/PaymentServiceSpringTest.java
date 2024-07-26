package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ExRateProviderStub exRateProviderStub;

    @Autowired
    Clock clock;

    @Test
    public void ConvertedAmount() throws IOException {
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        //exRate :500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
    }

    @Test
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService((new ExRateProviderStub(valueOf(1_000))), clock);
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        //valid until이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

}