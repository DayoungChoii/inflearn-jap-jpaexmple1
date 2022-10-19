package jpabook.jpaexmple1.domain;

import jpabook.jpaexmple1.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //비지니스 로직

    /**
     * 주문 취소
     */
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문상품 전체 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
