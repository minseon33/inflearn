package tobyspring.hellospring.order;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue // 아이디값 알아서 넣어줘라
    private Long id;
    @Column(unique = true) // 데이터가 고유해야 한다.
    private String no;

    private BigDecimal total;

    public Order() {
    }

    public Order(String no, BigDecimal total) {
        this.no = no;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", total=" + total +
                '}';
    }
}
