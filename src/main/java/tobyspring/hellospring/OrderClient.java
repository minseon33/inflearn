package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.OrderRepository;
import tobyspring.hellospring.order.Order;
import tobyspring.hellospring.order.OrderService;

import java.math.BigDecimal;

public class OrderClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
        OrderService orderService = beanFactory.getBean(OrderService.class);

        Order order = orderService.createOrder("0100", BigDecimal.TEN);
        System.out.println("Order : "+ order);


//        try {
//            //em
//            new TransactionTemplate(transactionManager).execute(status -> {
//
//                Order order = new Order("100", BigDecimal.TEN);
//                repository.save(order);
//                System.out.println("order : " + order);
//
//                return null;
//            });
//
//        }catch(DataIntegrityViolationException e){
//            System.out.println("주문번호 중복 복구 작업");
//        }



    }
}
