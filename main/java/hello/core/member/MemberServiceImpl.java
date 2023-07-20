package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    // 추상화에만 의존하게 됨 = DIP 규칙을 지킴
    private final MemberRepository memberRepository;

    @Autowired // ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // join에서 save() 호출하면, 다형성에 의해서 MemoryMemberRepository에 있는
    // save()가 호출됨
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Test용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    } // getMemberRepository()

} // end class
