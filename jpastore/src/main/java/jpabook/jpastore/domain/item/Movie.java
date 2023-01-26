package jpabook.jpastore.domain.item;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("M") // 구분짓는 칼럼(속성)값 dtype = M
public class Movie extends Item {
    private String director;
    private String actor;
}
