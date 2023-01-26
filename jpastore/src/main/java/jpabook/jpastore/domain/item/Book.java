package jpabook.jpastore.domain.item;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("B") // 구분짓는 칼럼(속성)값 dtype = B
public class Book extends Item {
    private String author;
    private String isbn;
}
