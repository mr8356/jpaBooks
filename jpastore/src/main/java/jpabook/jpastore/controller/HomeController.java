package jpabook.jpastore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import jpabook.jpastore.argumentresolver.Login;
import jpabook.jpastore.domain.Member;
import jpabook.jpastore.service.MemberService;
import jpabook.jpastore.session.SessionManager;

@Controller
public class HomeController {
    @Autowired private MemberService memberService;
    @Autowired private SessionManager sessionManager;

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member member, Model model)
        {
        if (member == null) {
            return "home";
        }

        // 로그인 완료
        model.addAttribute("member" , member);
        return "loginHome";
    }

    // @GetMapping("/")
    public String homeLoginV3(@SessionAttribute(name = "memberId", required = false) Long memberId, Model model)
        {

        if (memberId == null) {
        return "home";
        }

        Member member = memberService.findOne(memberId);
        if (member == null) {
            return "home";
        }

        // 로그인 완료
        model.addAttribute("member" , member);
        return "loginHome";
    }

    // @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model){

        Member member = (Member)sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        // 로그인 완료
        model.addAttribute("member" , member);
        return "loginHome";
    }
}