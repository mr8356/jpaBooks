package jpabook.jpastore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.domain.Order;
import jpabook.jpastore.domain.OrderSearch;
import jpabook.jpastore.domain.item.Item;
import jpabook.jpastore.service.ItemService;
import jpabook.jpastore.service.MemberService;
import jpabook.jpastore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String orderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String createOrder(@RequestParam("memberId") Long memberId,
                                @RequestParam("itemId") Long itemId,
                                    @RequestParam("count") int count)
    {
        orderService.order(memberId, itemId,count);
        return "redirect:/orders";
    }

    @GetMapping(value="/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch
    orderSearch, Model model) {
        List<Order> orders = orderService.findAllByCriteria(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
    

}
