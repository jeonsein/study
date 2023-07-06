package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    // EntityManager = JPA에서 엔티티의 영속성 관리!
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // persist() 호출로 Member 객체를 db에 저장!
        em.persist(member);

        // save() 호출 후에는 db에 저장된 Member객체 반환됨
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // find() 호출로 주어진 id에 해당하는 Member 객체를 db에서 조회
        // Member.class는 조회할 entity class를 지정함
        Member member = em.find(Member.class, id);

        // 조회된 Member 객체가 없는 경우, Optional.empty() 반환
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // createQuery() 호출해서 JPQL 쿼리 작성 -> Member 객체를 대상으로 db에서 조회함
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                // .setParamter를 통해 쿼리에 전달할 매개변수 설정
                .setParameter("name", name)
                // 쿼리 실행 후 결과를 List<Member> 형태로 반환!
                .getResultList();

        // 조회 결과 스트림으로 변환 후 findAny() 호출로 임의의 요소 반환
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // Jpql -> 기존의 SQL이 테이블을 대상으로 쿼리를 수행했다면, 이건 Entity 대상으로 쿼리를 날림
        // m = member 객체 대상으로!
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

} // end class
