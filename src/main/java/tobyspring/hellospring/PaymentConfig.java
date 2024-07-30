package tobyspring.hellospring;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRatePaymentProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration   //어떤빈 클래스와 어떤빈 클래스가 관계를 맺는다는 것을 알려주는 어노테이션 Configuration
@ComponentScan
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(),clock());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ExRateProvider exRateProvider(){
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
