package jpabook.jpastore.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jpabook.jpastore.domain.*;
import jpabook.jpastore.domain.Order;
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
    private final EntityManager em;

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

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
        Predicate status = cb.equal(o.get("status"),
       orderSearch.getOrderStatus());
        criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
        Predicate name =
        cb.like(m.<String>get("name"), "%" +
       orderSearch.getMemberName() + "%");
        criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대1000건
        return query.getResultList();
       }
}
