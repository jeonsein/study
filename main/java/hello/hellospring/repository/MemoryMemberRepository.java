package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    // 저장을 위해서 Map 사용
    // 이거 해당 Map static으로 선언 안해주면 별도로 Map이 생기기 때문에(인스턴스)
    // static으로 해줌!!
//    private Map<Long, Member> store = new HashMap<>();
    private static Map<Long, Member> store = new HashMap<>();

    // sequence는 private static long sequence = 0L;로 초기화되어 있으며,
    // 멤버를 저장할 때마다 1씩 증가하여 고유한 id 값을 생성하는 역할
    private static long sequence = 0L;  // 0, 1, 2 처럼 key 값을 생성해줌

    @Override
    public Member save(Member member) {

        // save할 때, sequence 값 하나 올려주고 store에 넣기 전에 Id 값 세팅
        // 매개변수로 전달받은 Member 객체의 id 값을 설정하기 위해
        // ++sequence를 사용하여 sequence 값을 1 증가시킵니다.
        member.setId(++sequence);

        // store에 저장
        // store.put(member.getId(), member);를 호출하여 store 맵에 멤버를 저장!
        // store는 private static Map<Long, Member> store = new HashMap<>();로 초기화되어 있으며,
        // id를 키로 하고 멤버 객체를 값으로 하는 맵입니다.
        store.put(member.getId(), member);

        // 저장된 멤버 객체(result) 반환
        return member;
    } // save()

    @Override
    public Optional<Member> findById(Long id) {
        // return store.get(id); // 만약 id값이 null이면?
        return Optional.ofNullable(store.get(id)); // Optional로 감싸서 null이어도 반환이 가능해짐
    } // findById()

    @Override
    public Optional<Member> findByName(String name) {
        // store(Map)에 저장된 값들을 스트림으로 변환! 각 회원은 Member 객체로 표현됨
        return store.values().stream()
                // member에서 getName()이 파라미터로 넘어온 name과 같은 회원만 필터링함
                // member.getName() = 회원의 이름을 가져옴
                .filter(member -> member.getName().equals(name))
                // 필터링된 회원 중 아무 회원이나 반환!
                .findAny();
        // 하나 찾으면 바로 반환하고 없으면 Optional로 인해서 null값 반환

    } // findByName()

    @Override
    public List<Member> findAll() {
        // store(Map)에 있는 회원 정보를 ArrayList에 담아서 반환함
        return new ArrayList<>(store.values());

    } // findAll()

    // Test 코드 수행 시 메모리 지우기 위해서
    public void clearStore() {
        store.clear();
    } // clearStore()

} // end class
