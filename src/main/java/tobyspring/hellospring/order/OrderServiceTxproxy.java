package tobyspring.hellospring.order;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceTxproxy implements OrderService{
    private final OrderService target;

    public OrderServiceTxproxy(OrderService target, PlatformTransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }

    private final PlatformTransactionManager transactionManager;

    @Override
    public Order createOrder(String no, BigDecimal total) {
        return target.createOrder(no, total);
    }

    @Override
    public List<Order> createOrders(List<OrderRequest> reqs) {
        return new TransactionTemplate(transactionManager).execute(status ->
            target.createOrders(reqs));

    }
}
