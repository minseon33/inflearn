package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.payment.Pament;
import tobyspring.hellospring.payment.PaymentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Pament pament1 = paymentService.prepare(100L,"USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment1 : " + pament1);
        System.out.println("--------------------------------------------------\n");

        TimeUnit.SECONDS.sleep(1);
        Pament pament2 = paymentService.prepare(100L,"USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment2 : " + pament2);

        System.out.println("--------------------------------------------------\n");

        TimeUnit.SECONDS.sleep(2);

        Pament pament3 = paymentService.prepare(100L,"USD", BigDecimal.valueOf(50.7));
        System.out.println("Payment3 : " + pament3);

    }


}
