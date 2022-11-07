package jpabook.jpaexmple1.api;

import jpabook.jpaexmple1.domain.Order;
import jpabook.jpaexmple1.domain.OrderSearch;
import jpabook.jpaexmple1.repository.OrderRepository;
import jpabook.jpaexmple1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //lazy 강제 초기화
            order.getDelivery().getAddress(); //lazy 강제 초기화e
        }
        return all;
    }
}
