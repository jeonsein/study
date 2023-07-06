package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(dataSource);
    } // end MemberRepository

    @Bean
    public MemberService memberService() {
        // MemberService가 MemberRepository를 의존하고 있으며, 스프링이 이 의존성을 자동으로 주입하기 위한 설정
        return new MemberService(memberRepository());
    } // end MemberService

//    @Bean
//    public MemberRepository memberRepository() {
//        // interface는 new가 안됨!
//        // 구현체로 new()
//        return new MemoryMemberRepository();
//    } // end MemberRepository

} // end class
