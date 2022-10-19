package jpabook.jpaexmple1.service;

import jpabook.jpaexmple1.domain.item.Book;
import jpabook.jpaexmple1.domain.item.Item;
import jpabook.jpaexmple1.domain.item.Movie;
import jpabook.jpaexmple1.exception.NotEnoughStockException;
import jpabook.jpaexmple1.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 아이템_저장() throws Exception{
        //given
        Item item = new Book();
        item.setName("무소유");
        item.setPrice(12000);
        item.setStockQuantity(100);

        //when
        itemService.save(item);

        //then
        assertThat(item).isEqualTo(itemService.findItem(item.getId()));

    }

    @Test
    public void 아이템_개수_증가() throws Exception{
        //given
        Item item = new Book();
        item.setName("무소유");
        item.setPrice(12000);
        item.setStockQuantity(100);

        //when
        item.addStock(1);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(101);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 아이템_개수_감소_예외발생() throws Exception{
        //given
        Item item = new Book();
        item.setName("무소유");
        item.setPrice(12000);
        item.setStockQuantity(0);

        //when
        item.removeStock(12);

        //then
        fail();

    }

    @Test
    public void 아이템_개수_감소() throws Exception{
        //given
        Item item = new Book();
        item.setName("무소유");
        item.setPrice(12000);
        item.setStockQuantity(10);

        //when
        item.removeStock(5);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(5);

    }

    @Test
    public void 아이템_찾기() throws Exception{
        //given
        Item item = new Book();
        item.setName("무소유");
        item.setPrice(12000);
        item.setStockQuantity(100);

        itemService.save(item);

        //when
        Item foundItem = itemService.findItem(item.getId());

        //then
        assertThat(item).isEqualTo(foundItem);

    }

    @Test
    public void 아이템_리스트_찾기() throws Exception{
        //given
        Item item1 = new Book();
        item1.setName("무소유");
        item1.setPrice(12000);
        item1.setStockQuantity(100);

        Item item2 = new Movie();
        item2.setName("탑건");
        item2.setPrice(16000);
        item2.setStockQuantity(50);

        itemService.save(item1);
        itemService.save(item2);

        //when
        List<Item> items = itemService.findItems();

        //then
        assertThat(items.size()).isEqualTo(2);

    }
}