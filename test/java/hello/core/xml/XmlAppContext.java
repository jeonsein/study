package hello.core.xml;

import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContext {

    @Test
    void xmlAppContext() {
        // "appConfig.xml"을 사용 GenericXmlApplicationContext 생성
        // GenericXmlApplicationContext
        // -> 스프링 컨테이너의 구현체 중 하나로서,
        // XML 기반의 설정 파일을 로드하여 컨테이너를 초기화!
        ApplicationContext ac =
                new GenericXmlApplicationContext("appConfig.xml");

        MemberService memberService = ac.getBean("memberService", MemberService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);

    } // xmlAppContext()

} // end class
