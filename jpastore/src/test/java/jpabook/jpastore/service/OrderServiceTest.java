package jpabook.jpastore.service;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpastore.domain.*;
import jpabook.jpastore.domain.item.*;
import jpabook.jpastore.exception.NotEnoughStockException;
import jpabook.jpastore.repository.OrderRepository;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired EntityManager em;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        // given
        Member member = creatMember("홍길동");
        Item book = createBook("boooook");
        int orderCount = 2;
        
        // when - OrderService
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order resultOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals( OrderStatus.ORDER, resultOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(1, resultOrder.getOrderItems().size() ,"주문한 상품수가 정확해야한다");
        Assertions.assertEquals(10000 * orderCount, resultOrder.getTotalPrice(), "주문한 상품의 총 가격은 가격 * 주문수량이다.");
        Assertions.assertEquals(10-2 ,book.getStockQuantity(), "주문한 수량만큼 수량이 줄어들어야 한다.");
    }

    @Test
    public void 주문수량_초과() throws NotEnoughStockException{
        // given
        Member member = creatMember("홍석천");
        Item book = createBook("많은책들");
        int orderCount = 11;

        // when
        Assertions.assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
        // orderService.order(member.getId(), book.getId(), orderCount);

        // then
        // Assertions.fail("재고 수량 부족오류가 나와야한다.");
    }

    @Test
    public void 주문_취소() {
        Member member = creatMember("홍");
        Item book = createBook("책들");
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //when
        orderService.cancleOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.CANCLE, order.getStatus());
        Assertions.assertEquals(10, book.getStockQuantity());
    }

    private Member creatMember(String name) {
        Member member = new Member();
        member.setAddress(new Address("서울","강가","123-123"));
        member.setName(name);
        em.persist(member);
        return member;
    }

    private Item createBook(String name){
        Item book = new Book();
        book.setPrice(10000);
        book.setStockQuantity(10);
        book.setName(name);
        em.persist(book);
        return book;
    }

}
