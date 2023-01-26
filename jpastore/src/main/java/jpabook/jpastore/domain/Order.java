package jpabook.jpastore.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.*;

@Entity
@Getter
@Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //FK = Member테이블의 member_id
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status; // 주문 상태 [ORDER, CANCLE]
}
