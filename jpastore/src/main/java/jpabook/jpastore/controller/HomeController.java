package jpabook.jpastore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.service.MemberService;

@Controller
public class HomeController {
    @Autowired private MemberService memberService;

    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId" , required = false) Long memberId, Model model){
        if (memberId == null) {// 멤버 로그인 정보가 담겨진 쿠키가 없다면 = 미로그인 상태
            return "home";
        }

        //로그인 (쿠기 존재)
        Member member = memberService.findOne(memberId);
        if (member == null) { // 쿠키오류. 해당 멤버 존재하지 않음
            return "home";
        }

        // 로그인 완료
        model.addAttribute("member" , member);
        return "loginHome";
    }
}