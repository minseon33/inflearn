package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.ObjectFactory;
import tobyspring.hellospring.TestObjectFactory;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestObjectFactory.class)
class PaymentServiceSpringTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ExRateProviderStub exRateProviderStub;

    @Test
    public void ConvertedAmount() throws IOException {
        Pament pament = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(pament.getExRate()).isEqualByComparingTo(valueOf(1000));
        assertThat(pament.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        //exRate :500
        exRateProviderStub.setExRate(valueOf(500));
        Pament pament2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        assertThat(pament2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(pament2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));


    }

}