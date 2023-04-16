package jpabook.jpastore.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //FK = Member테이블의 member_id
    private Member member;

    // 하위 엔티티에도 persist 명령 전달 => Cascade.ALL 
    // order를 저장할때 아이템과 딜리버리는 자동저장해야되므로 -> cascade
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deilvery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCLE]

    //== 연관관계 편이 양방향 메소드 ==//
    /*
     * 보통 order 추가할때 member.getOrders().add(order); order.setMember(member); 하지만,
     * 아래로 오버로딩해주면 order.setMember(order) 하면 끝남. === 보통 member가 order를 추가하므로. 없어도 되지만 있으면 더 편함 
     */
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem...orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    //== 비즈니스 로직 메서드 ==//
    /*
     * 주문 취소
     */
    public void cancle() {
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소할 수 없습니다.");
        }
        this.setStatus(OrderStatus.CANCLE);
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancle(); // OrderItem 엔티티에 이 코드 작성하기//
        }
    }



    //== 조회 로직 ==//
    public int getTotalPrice() {
        int sum =0;
        for (OrderItem orderItem : orderItems) {
            sum += orderItem.getTotalPrice();
        }
        return sum;
    }

}
