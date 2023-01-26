package jpabook.jpastore.domain.item;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("A") // 구분짓는 칼럼(속성)값 dtype = A
public class Album extends Item {
    private String artist;
    private String etc;
}
