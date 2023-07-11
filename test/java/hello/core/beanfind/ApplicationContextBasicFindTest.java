package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);

//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass = " + memberService.getClass());

        // 검증
        // memberService의 객체 인스턴스가 뭔지!
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        // -> 성공 memberService는 memberServiceImpl의 객체 인스턴스임을 알 수 있다!

    } // findBeanByName()

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    } // findBeanByType()

    @Test
    @DisplayName("구체타입으로 조회")
    void findBeanByName2() {
        MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    } // findBeanByName2()

    @Test
    @DisplayName("빈 이름으로 조회 X")
    void findBeanByNameX() {
        // ac.getBean("XXXX", MemberService.class);
//         ac.getBean("XXXX", MemberService.class);

         // 왼쪽의 예외가 안터지면 실패! 터지면 성공!
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("XXXX", MemberService.class));

    } // findBeanByNameX()
    
} // end class
