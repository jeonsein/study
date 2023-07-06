package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

//    @Autowired
    // -> 생성자가 딱 하나만 있으면 @Autowired 생략 가능!
    // DataSource를 기반으로 JdbcTemplate 객체를 생성하여 멤버 변수 jdbcTemplate에 할당
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    } // JdbcTemplateMemberRepository()

    // 그냥 이런거구나 하고 알아두기!!
    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert = 편리한 방법으로 INSERT 문을 생성하고 실행하는 데 사용되는 클래스
        // 테이블 이름과 생성된 키 열을 지정할 수 있음
        // jdbcTemplate 객체 전달로 데이터 베이스 연결 설정함
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // 객체에 삽입할 테이블 이름 설정, 생성된 키 열 지정함
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        // 데이터베이스에 삽입하기 위한 데이터를 저장할 parameters Map 생성
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        // 지정된 테이블에 데이터를 삽입하고 생성된 키 값을 반환
        // MapSqlParamterSource를 사용하여 삽입할 데이터 제공
        // jdbcInsert 객체의 executeAndReturnKey() 호출 -> 데이터 삽입하고, 생성된 키 값을 획득
        // MapSqlParameterSource를 사용하여 parameters 맵 전달함
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // 가져온 key 값을 long 형태로 변환하여 member 객체의 id에 설정
        // -> 이를 통해 데이터베이스에서 생성된 키 값을 Member 객체에 할당!
        member.setId(key.longValue());

        // id값이 설정되어 있는 상태!
        return member;
    }

    // 조회
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result =
                // id값이 주어진 매개 변수와 일치하는 회원 조회하는 쿼리 실행.
                // ?를 사용하여 매개 변수 전달함.
                // memberRowMapper()를 통해 ResultSet의 각 행을 Member 객체로 매핑하는
                // RowMapper를 반환
                jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);

        // 조회된 결과를 스트림으로 변환 => findAny()호출로 임의의 요소를 반환함.
        // 이는 Optional<Member> 형태로 반환됨
        return result.stream().findAny();
    }

    // 조회
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result =
                jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // RowMapper = ResultSet의 각 행을 객체로 매핑하는 인터페이스!
    // RowMapper인터페이스의 익명 클래스를 생성, Member 객체에 해당하는 ResultSet 열의 값 매핑함
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();

            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            return member;
        };
    } // memberRowMapper()

} // end class
