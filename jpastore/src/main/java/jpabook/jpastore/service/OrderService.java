package jpabook.jpastore.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpastore.domain.*;
import jpabook.jpastore.domain.item.*;
import jpabook.jpastore.repository.ItemRepository;
import jpabook.jpastore.repository.MemberRepository;
import jpabook.jpastore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /*
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성 - 임시
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        
        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, count, item.getPrice());

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장 - Cascade 옵션으로 delivery orderitem처럼 연관된것들도 다 저장.
        orderRepository.save(order);

        return order.getId();
    }

    /*
     * 취소
     */
    @Transactional
    public void cancleOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancle();
    }
}
