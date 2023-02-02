package jpabook.jpastore.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
// 읽기 전용으로 설정하면 최적화 됨.
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    // 회원가입만 읽기전용 예외
    @Transactional
    public Long join(Member member) {
        vaildateOverlapMember(member);
        this.memberRepository.save(member);
        return member.getId();
    }

    private void vaildateOverlapMember(Member member){
        List<Member> result= this.memberRepository.findByName(member.getName());
        if(!result.isEmpty()){
            throw new IllegalStateException("already exsists");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return this.memberRepository.findAll();
    }

    // 지정된 회원 정보 찾기
    public Member findOne(Long id) {
        return this.memberRepository.findOne(id);
    }
}
