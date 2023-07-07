package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// interface가 interface 상속 받을 때는 extends!
// SpringDataJpaMemberRepository 인터페이스는 JpaRepository<Member, Long>를 상속받았으며,
// MemberRepository 인터페이스를 구현합니다.
// JpaRepository를 상속함으로써 기본적인 CRUD 작업에 대한 메서드를 이미 갖게 됨!
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 구현 안해도 댐!!!
    @Override
    // Member 객체를 이름으로 조회하는 메서드 -> JpaRepository에서 기본적으로 제공
    // Spring Data JPA는 메서드 이름을 분석하여 해당 필드를 기준으로 자동으로 쿼리를 생성하고 실행
    // JPQL 쿼리 -> (select m from Member m where m.name = ?) 를 실행하여 이름에 해당하는 회원 조회함
    Optional<Member> findByName(String name);

} // end interface
