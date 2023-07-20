package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {

    // 저장소니까 Map을 활용!
    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 동시성 이슈가 있을 수 있기 때문에
    // concurrent hashmap을 사용하는 것이 좋음

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }

} // end class
