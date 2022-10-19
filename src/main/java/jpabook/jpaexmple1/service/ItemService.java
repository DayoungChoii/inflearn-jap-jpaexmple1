package jpabook.jpaexmple1.service;

import jpabook.jpaexmple1.domain.item.Item;
import jpabook.jpaexmple1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void save(Item item){
        itemRepository.save(item);
    }

    public Item findItem(Long id){
        return itemRepository.findOne(id);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
