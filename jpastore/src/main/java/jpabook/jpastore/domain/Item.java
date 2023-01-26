package jpabook.jpastore.domain;

import javax.persistence.*;

public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    
    
}
