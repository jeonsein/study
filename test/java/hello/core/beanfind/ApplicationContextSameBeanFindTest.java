package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 둘 이상 있으면 중복 오류가 발생한다.")
    void findBeanByTypeDuplicate() {
        // 조회하면 memberRepository1과 memberRepository2 조회 -> 오류 발생
//        MemberRepository bean = ac.getBean(MemberRepository.class);

        // test는 성공해야 예외 발생햇다는 의미!!
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));

    } // findBeanByTypeDuplicate()

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 둘 이상 있으면 빈 이름을 지정하면 된다!")
    void findBeanByName() {
        MemberRepository memberRepository =
                ac.getBean("memberRepository1", MemberRepository.class);

        assertThat(memberRepository).isInstanceOf(MemberRepository.class);

    } // findBeanByName()

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        } // for
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    } // findAllBeanByType()


    // AppConfig 파일 수정하기 싫어서 테스트 코드 내부에 생성하기
    // class 내부의 class이기 때문에 scope이 해당 클래스 내부로 한정됨
    @Configuration
    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }

    } // end class

} // end class
