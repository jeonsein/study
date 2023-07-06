package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member); // 회원을 저장소에 저장하는 method
    // id나 name이 null값일 경우, null을 Optional로 감싸서 처리해줌.
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 저장된 모든 회원 리스트 반환

} // end interface
