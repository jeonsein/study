package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
// test 실행할 때 transaction을 실행하고,
// db에 데이터 넣은 후에 그걸 다시 롤백해줘서 데이터 다 날아감 -> 다음 테스트 반복 실행이 가능해짐!
@Transactional
class MemberServiceIntegrationTest {

    // test는 내가 필요한거 필드 기반으로 바로 @Autowired하는게 편함!
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {

        // Given
        Member member = new Member();
        member.setName("spring");

        // When
        Long saveId = memberService.join(member);

        // Then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    } // 회원가입()

    @Test
    public void 중복_회원_예외() {

        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    } // 중복_회원_예외()

} // end class