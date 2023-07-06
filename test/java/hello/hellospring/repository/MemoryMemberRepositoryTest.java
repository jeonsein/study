package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    // MemberRepository 인터페이스를 구현한 MemoryMemberRepository 객체를 생성.
    // -> MemberRepository의 메소드들 사용 가능하도록!
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // save() 동작하는 지 테스트
    // 해당 메소드는 member 객체를 생성하고 이름을 설정한 후,
    // repository의 save 메소드를 사용하여 member를 저장하고,
    // 저장된 member를 조회하여 동일한지 확인하는 기능을 테스트하도록 설계되었음.

    // Test 수행 시, Test가 끝날 때 마다 코드를 깔끔하게 지워주기 위해
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    } // afterEach()

    @Test
    public void save() {
        // Member 객체 생성
        Member member = new Member();
        // member 객체에 spring이라고 setName()!
        member.setName("spring");

        // repository의 save() 호출해서 member를 저장해주기
        // 이때 호출된 member는 고유한 ID를 가지게 됨
        repository.save(member);

        // 검증
        // 저장된 member를 조회하기 위해 findById 메소드를 호출!
        // findById() -> member의 ID를 인자로 받아 해당 ID와 일치하는 회원을 찾아 반환.
        // get() 호출하여 Optional 객체에서 실제 회원 객체인 result를 가져옴!
        Member result = repository.findById(member.getId()).get();

        // result와 member가 동일한지 비교하여 결과를 출력
        // 동일하다면 true 동일하지 않다면 false 출력
        // System.out.println("result = " + (result == member));

        // Assertions.assertEquals(member, result);
        assertThat(result).isEqualTo(member);

    } // save()

    @Test
    public void findByName() {

        // Member 객체 member1을 생성하고 setName("Spring1")으로 이름을 설정
        Member member1 = new Member();
        member1.setName("Spring1");
        // repository.save(member1)을 호출하여 member1을 저장.
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        // repository.findByName("Spring1")을 호출하여
        // 이름이 "Spring1"인 멤버 탐색
        // 이후 get() 메소드를 사용하여 Optional 객체에서 실제 Member 객체 result를 가져옴!
         Member result = repository.findByName("Spring1").get();
         // Member result = repository.findByName("spring2").get();
        // -> 오류남. assertThat(result)에서 result가 member2이기 때문에
        // .isEqualTo(member1)과 일치하지 않음

        // assertThat(result).isEqualTo(member1)을 사용하여
        // result와 member1이 동일한지 확인
        assertThat(result).isEqualTo(member1);

    } // findByName()

    @Test
    public void findAll() {

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // repository.findAll()을 호출하여 모든 멤버를 가져옴
        // 반환된 결과인 List<Member> 객체 result를 가져옴
        List<Member> result = repository.findAll();

        // 2개 넣었으니까 result.size()했을 때 2가 떠야함!
        assertThat(result.size()).isEqualTo(2);

    } // findAll()

    @Test
    public void 메모리멤버리파지토리테스트() {

        // MemoryMemberRepository에서 static으로 Map 선언 안 할 경우
        // 진짜 다른 저장소(Map)를 사용하는지 확인해보려고!
        MemoryMemberRepository mmr1 = new MemoryMemberRepository();
        MemoryMemberRepository mmr2 = new MemoryMemberRepository();

        Member member1 = new Member();

        member1.setId(1L);
        member1.setName("셍나테스트1216");

        mmr1.save(member1);

        mmr1.findById(1L)
                .ifPresent(m -> System.out.println("mmr1" + member1.getName()));
        mmr2.findById(1L)
                .ifPresent(m -> System.out.println("mmr2" + member1.getName()));

        // static 안붙인 결과: mmr1셍나테스트1216
        // -> 서로 다른 DB 사용하고 있음

        // static 붙여주면 아래처럼 결과 확인 가능함! -> 같은 DB 사용!
        // mmr1셍나테스트1216
        // mmr2셍나테스트1216

    } // end 메모리멤버리파지토리테스트()

} // end class
