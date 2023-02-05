package jpabook.jpastore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpabook.jpastore.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    //== 생성 로직 ==//
    public static OrderItem createOrderItem(Item item, int count, int price) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(price);
        orderItem.setCount(count);
        item.removeStock(count);

        return orderItem;
    }

    //== 비즈니스 로직 ==//
    public void cancle() {
        this.item.addStock(count);
    }

    //== 조회 로직 ==//
    public int getTotalPrice() {
        return count * orderPrice;
    }
}
