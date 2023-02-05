package jpabook.jpastore.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpastore.domain.Member;
import jpabook.jpastore.repository.MemberRepository;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long id = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberService.findOne(id));
    }

    @Test()
    public void 회원_중복_예외() {
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        //then
        Assertions.fail("오류가 발생해야합니다.");
    }


}
