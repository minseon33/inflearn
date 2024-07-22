package tobyspring.hellospring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.ExRateProviderStub;
import tobyspring.hellospring.payment.PaymentService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration   //어떤빈 클래스와 어떤빈 클래스가 관계를 맺는다는 것을 알려주는 어노테이션 Configuration
@ComponentScan
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(),clock());
    }

//    @Bean
//    public PaymentService paymentService() {
//        return new PaymentService(cashedExRateProvider());
//    }

//    @Bean
//    public ExRateProvider cashedExRateProvider(){
//        return new CachedExRateProvider(exRateProvider());
//    }

    @Bean
    public ExRateProvider exRateProvider(){
        return new ExRateProviderStub(BigDecimal.valueOf(1000));
    }

    @Bean
    public Clock clock(){
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
