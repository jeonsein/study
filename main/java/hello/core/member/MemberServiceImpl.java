package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // 추상화에만 의존하게 됨 = DIP 규칙을 지킴
    private final MemberRepository memberRepository;

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

} // end class
