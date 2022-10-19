package jpabook.jpaexmple1.domain.item;

import jpabook.jpaexmple1.exception.NotEnoughStockException;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long Id;

    private String name;
    private int price;
    private int stockQuantity;

    //비지니스 로직

    /**
     * 수량 증가
     * @param quantity
     */
    public void addStock(int quantity){
        int restStock = this.stockQuantity - quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
