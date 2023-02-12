package jpabook.jpastore.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import jpabook.jpastore.domain.item.*;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // 완전히 새로운 객체이면
            em.persist(item);
        } else { // id 이미 존재 -> 이미 넣었음.
            em.merge(item); // update 
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
