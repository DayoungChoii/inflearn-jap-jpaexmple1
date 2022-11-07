package jpabook.jpaexmple1;

import jpabook.jpaexmple1.domain.*;
import jpabook.jpaexmple1.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @RequiredArgsConstructor
    @Component
    @Transactional
    static class InitService {

        private final EntityManager em;

        /**
         * USER A
         *  * JPA BOOK1
         *  * JPA BOOK2
         * USER B
         *  * SPRING BOOK1
         *  * SPRING BOOK2
         */
        public void dbInit1() {
            Member member = createMember("서울", "1", "1111", "userA");
            em.persist(member);

            Book book1 = createBook("JPA BOOK1", 10000, 100);
            Book book2 = createBook("JPA BOOK2", 20000, 100);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);

        }

        public void dbInit2() {
            Member member = createMember("충북", "1", "1111", "userB");
            em.persist(member);

            Book book1 = createBook("SPRING BOOK1", 20000, 200);
            Book book2 = createBook("SPRING BOOK2", 40000, 300);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);

        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);

            return book;
        }

        private Member createMember(String city, String street, String zipcode, String name) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}
