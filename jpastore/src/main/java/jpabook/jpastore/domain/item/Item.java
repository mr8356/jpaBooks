package jpabook.jpastore.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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

    //  Item -> OrderItem 방향으로 가지 않기 때문에 List<OrderItem> 안함


    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();

}
