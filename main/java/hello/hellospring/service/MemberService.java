package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service 클래스는 비즈니스에 가까운 용어 사용해서 메소드명을 작성함!
//@Service
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // MemberService

    // 회원 가입
    public Long join(Member member) {
        // 조건) 같은 이름이 있는 중복 회원 X

        validateDuplicateMember(member); // 중복 회원 검증

        /* Optional 사용 방법 #1
        // Optional로 반환 -> ifPresent 사용 가능.
        Optional<Member> result = memberRepository.findByName(member.getName());
        // 값 바로 꺼내려면 (권장 X)
//        result.get();
        // 값이 있으면 꺼내고 없으면 ~메소드를 실행해라, default 값을 꺼내라
//        result.orElseGet();

        // result에 이미 값이 존재한다면, IllegalStateException 던지기
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        // 중복 회원 검증 이후 통과하면 저장
        memberRepository.save(member);
        return member.getId();

    } // join()

    // Optional 사용 방법 #2
    // 매개변수로 전달된 Member 객체의 이름과 동일 이름을 가진 회원이 이미 존재하는지를 검증하는 메소드
    private void validateDuplicateMember(Member member) {
        // memberRepository에서 동일 이름을 가진 회원을 조회
        // -> 해당 메소드는 해당 이름을 가진 회원을 Optional<Member> 형태로 반환함
        // findByName()의 시그니처에 따라 반환 타입이 Optional<Member>로 선언되어 있기 때문임!
        memberRepository.findByName(member.getName())
                // 반환된 Optional<Member> 객체에 대해 .ifPresent() 호출
                // 값이 존재하는 경우에만 람다식 내부의 코드 실행!
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    } // validateDuplicateMember()

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    } // findMembers()

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    } // findOne()

} // end class
