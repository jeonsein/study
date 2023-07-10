package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // 생성자 주입
    // MemberServiceImpl을 생성하고, 해당 구현체는 MemoryMemberRepository를 사용함! (주입)
    @Bean
    public MemberService memberService() {
        // memberRepository()를 의존
        // 따라서 MemberServiceImpl은 MemberRepository에 의존.
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // MemoryMemberRepository는 MemberRepository 인터페이스를 구현한 구현체
        // MemberServiceImpl은 MemberRepository에 의존하여 데이터 저장소를 사용
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        // memberRepository()와 discountPolicy()를 의존
        // 따라서 OrderServiceImpl은 MemberRepository와 DiscountPolicy에 의존
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // FixDiscountPolicy는 DiscountPolicy 인터페이스를 구현한 구현체
        // OrderServiceImpl은 DiscountPolicy에 의존하여 할인 정책을 사용
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

} // end class