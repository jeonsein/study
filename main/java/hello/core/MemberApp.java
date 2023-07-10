package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();

        // appConfig.memberService()하면, MemberService 인페이스를 줌!
        // MemberService에는 MemberServiceImpl 들어가 있음!
//        MemberService memberService = appConfig.memberService();

        // AppConfig 내부의 @Bean 붙은 것들을 다 관리해줌
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService =
                applicationContext.getBean("memberService", MemberService.class);

        // (L = Long)
        // id가 long 타입이라서 붙여줘야함
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());

    } // end main

} // end class
