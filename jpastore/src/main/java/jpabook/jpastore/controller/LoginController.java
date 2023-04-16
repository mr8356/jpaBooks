package jpabook.jpastore.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(
        @Valid @ModelAttribute("loginForm") LoginForm loginForm,
        BindingResult bindingResult,
        HttpServletResponse response)
    {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }//폼 부족하게 입력
        Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", member);
        if (member == null) {
            bindingResult.reject("loginFail","id or password incorrect");
            return "login/loginForm";
        }

        Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}