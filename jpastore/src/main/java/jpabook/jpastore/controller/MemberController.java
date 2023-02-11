package jpabook.jpastore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpastore.domain.Address;
import jpabook.jpastore.domain.Member;
import jpabook.jpastore.service.MemberService;
import jpabook.jpastore.web.MemberForm;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {

        // *******************************
        // if (result.hasErrors()) {
        //     return "members/createMemberForm";
        // }
        
        Member member = new Member();
        member.setName(form.getName());
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        member.setAddress(address);
        memberService.join(member); 
        return "redirect:/";
    }

    @GetMapping(value="/members")
    public String list(Model model) {
        List<Member> memberList = memberService.findMembers();
        model.addAttribute("members",memberList);
        return "members/memberList";
    }
    
}
