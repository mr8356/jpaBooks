package jpabook.jpastore.service;

import org.springframework.stereotype.Service;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){
        return memberRepository.findByLoginId(loginId).stream().filter(m -> m.getPassword().equals(password)).findAny().orElse(null);
    }
}
