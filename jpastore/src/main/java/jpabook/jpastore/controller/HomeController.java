package jpabook.jpastore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.service.MemberService;
import jpabook.jpastore.session.SessionManager;

@Controller
public class HomeController {
    @Autowired private MemberService memberService;
    @Autowired private SessionManager sessionManager;
    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model){
        // //로그인 (쿠기 존재)
        // Member member = memberService.findOne(memberId);
        // if (member == null) { // 쿠키오류. 해당 멤버 존재하지 않음
        //     return "home";
        // }

        Member member = (Member)sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        // 로그인 완료
        model.addAttribute("member" , member);
        return "loginHome";
    }
}