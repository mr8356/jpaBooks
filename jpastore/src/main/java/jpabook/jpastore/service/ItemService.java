package jpabook.jpastore.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpastore.domain.item.Item;
import jpabook.jpastore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    
    @Transactional
    public void saveItem(Item item) {
        this.itemRepository.save(item);
    }

    /**
     * new Item() => 준영속 == jpa가 관리 안함 -> merge() 필요
     * merge는 새로들어온 객체가 비어있는 항목은 다 null로 만들 위험이 있으므로 권장X
     * 
     * 아래와 같이 변하고 싶은 속성만 변환시키는게 좋음.
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        // findOne을 통해서 얻은 객체 -> 영속엔티티 -> set 함수를 쓰면 알아서 추적해서 변환해줌
        Item item = findOne(itemId); // 영속화
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return this.itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return this.itemRepository.findOne(id);
    }
}
