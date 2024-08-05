package tobyspring.hellospring.data;

import jakarta.persistence.*;
import tobyspring.hellospring.order.Order;

import java.math.BigDecimal;

public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Order order){
        entityManager.persist(order);
    }
}
