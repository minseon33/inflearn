package tobyspring.hellospring.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.OrderConfig;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceImplSpringTest {

    @Autowired
    OrderService orderService;

    @Autowired
    DataSource dataSource;

    @DisplayName("Order을 생성한다.")
    @Test
    void createOrder(){
        Order order = orderService.createOrder("0100", BigDecimal.ONE);
        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }

    @DisplayName("여러개의 Order을 생성한다.")
    @Test
    void createOrders(){
        List<OrderRequest> orderRequests = List.of(new OrderRequest("0200", BigDecimal.ONE),
                new OrderRequest("0201", BigDecimal.TWO)
        );

        List<Order> orders = orderService.createOrders(orderRequests);

        Assertions.assertThat(orders).hasSize(2);
        orders.forEach(order -> {
            Assertions.assertThat(order.getId()).isGreaterThan(0);
        });

    }


    @DisplayName("주문번호는 동일한 값이 들어갈 수 없다.")
    @Test
    void createDuplicatedOrders(){
        List<OrderRequest> orderRequests = List.of(new OrderRequest("0300", BigDecimal.ONE),
                new OrderRequest("0300", BigDecimal.TWO)
        );


        Assertions.assertThatThrownBy(() -> orderService.createOrders(orderRequests)).isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        Long count = client.sql("select count(*) from orders where no = '0300'").query(Long.class).single();
        Assertions.assertThat(count).isEqualTo(0);


    }


}
