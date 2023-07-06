package hello.hellospring;

import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

//    private DataSource dataSource;
//
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em); // EntityManager가 필요하기 때문에 필드랑 생성자 선언
    } // end MemberRepository

    @Bean
    public MemberService memberService() {
        // MemberService가 MemberRepository를 의존하고 있으며, 스프링이 이 의존성을 자동으로 주입하기 위한 설정
        return new MemberService(memberRepository());
    } // end MemberService

} // end class
