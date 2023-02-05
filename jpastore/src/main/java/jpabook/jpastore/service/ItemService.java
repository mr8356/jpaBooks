package jpabook.jpastore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    public void saveItem(Item item) {
        this.itemRepository.save(item);
    }

    public List<Item> findItems() {
        return this.itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return this.itemRepository.findOne(id);
    }
}
