package jpabook.jpastore.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import jpabook.jpastore.exception.NotEnoughStockException;
import lombok.*;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속된 객체들도 한 테이블에 다 짬뽕시키기
@DiscriminatorColumn(name = "dtype") // 상속된 객체마다 구분해주는 속성이름
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    //  Item -> OrderItem 방향으로 가지 않기 때문에 List<OrderItem> 안함


    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();


    //==비즈니스 로직==// 객체 안에다 정의
    // 무지성 setter 이용하지 말고 아래 비즈니즈 로직을 이용한다.

    /*
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /*
     * stock 감소
     */
    public void removeStock(int quantity) {
        if (quantity > this.stockQuantity) {
            throw new NotEnoughStockException("need more stock");
        }
    }
}
