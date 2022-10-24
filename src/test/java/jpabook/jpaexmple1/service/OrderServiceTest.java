package jpabook.jpaexmple1.service;

import jpabook.jpaexmple1.domain.Address;
import jpabook.jpaexmple1.domain.Member;
import jpabook.jpaexmple1.domain.Order;
import jpabook.jpaexmple1.domain.OrderStatus;
import jpabook.jpaexmple1.domain.item.Book;
import jpabook.jpaexmple1.domain.item.Item;
import jpabook.jpaexmple1.exception.NotEnoughStockException;
import jpabook.jpaexmple1.repository.OrderRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();

        Item book = createItem("무소유", 12000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

//        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 12000 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량_초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createItem("무소유", 10000, 10);

        int orderCount = 11;

        //when
        orderService.order(member.getId(), item.getId(), 11);
        
        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }
    
    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item item = createItem("무소유", 10000, 10);

        int orderCount = 5;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소 시 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());

    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("choi");
        member.setAddress(new Address("서울", "압구정로", "034-3"));
        em.persist(member);
        return member;
    }
}