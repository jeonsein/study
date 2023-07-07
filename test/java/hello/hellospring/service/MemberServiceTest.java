package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 테스트에서는 테스트가 서로 독립적인 동작해야 하므로
    // @BeforeEach를 통해 매번 MemoryMemberRepository를 새롭게 생성하여 사용하도록 구성했습니다.
    // 만약 이렇게 구성하지 않는다면 테스트 메서드마다 동일한 MemoryMemberRepository를 사용할테고
    // 데이터가 꼬여서 테스트가 실패할 수 있게 됩니다.
    @BeforeEach
    public void beforeEach() {
        // 각 테스트 실행 전에, MemoryMemberRepository를 만들고, memberRepository에 넣어줌
        // 이후 MemberService에다가 넣어줌!
        // 동일 MemoryMemberRepository 사용! -> 외부에서 넣어줌 = DI
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    } // beforeEach()

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    } // afterEach()

    // Test는 한글로 작성해도 괜찮!
    @Test
    // void join() {
    void 회원가입() {

        // given -> 무언가 주어짐(해당 데이터를 가지고)
        Member member = new Member();
        member.setName("hello");

        // when -> 이걸 실행했을 때
        Long saveId = memberService.join(member);

        // then -> 결과가 이렇게 나와야 함 (검증부)
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    } // 회원가입()

    @Test
    public void 중복_회원_예외() {
        // given -> 중복된 이름을 가진 회원 객체를 생성
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

//        -------------------------------

        // when -> 실제 테스트하고자 하는 메소드인 memberService.join(member1)을 실행하여 첫 번째 회원을 가입.
        // -> member1과 member2의 이름이 spring으로 동일하기 때문에 validateDuplicateMember()에서 예외 발생함
        memberService.join(member1);

//        ------------------------------

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        // 예외 객체 e의 메시지를 검증하는 부분
        // e.getMessage()를 통해 예외의 메시지를 가져와서 예상한 메시지와 비교.
        // 예상한 메시지와 일치하면 테스트 성공.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        -------------------------------

        // IllegalStateException이 예상되는 상황에서 memberService.join(member2)를 호출하고, 예외가 발생하는지를 테스트하는 코드
        // IllegalStateException.class -> 기대하는 예외의 클래스를 지정하고
        // 람다 표현식을 사용해서 테스트할 코드를 작성함. 이때, 해당 코드 블록에서 예외 발생해야 함!
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

//        --------------------------------

        /*
        // try-catch 구문을 사용하여 두 번째 회원인 member2를 가입시키는데, 예외가 발생하는지 확인!
        try {
            memberService.join(member2);
            fail();

        } catch (IllegalStateException e) {
//            catch 블록: IllegalStateException 예외가 발생해야 함
//            예외가 발생하지 않을 경우 -> fail() 메소드를 호출, 테스트를 실패시킴.
//            예외가 발생한 경우 -> 예외 메시지를 검증하고,
//            예상한 예외 메시지와 실제 발생한 예외 메시지를 비교하여 일치하는지 확인.
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다. 룰루랄라");
        } // try-catch
         */

        // then

    } // 중복_회원_예외()

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}