package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : 사용자 A가 10000원 주문
//        statefulService1.order("userA", 10000);
        // ThreadB : 사용자 B가 20000원 주문
//        statefulService2.order("userB", 20000);

        int userAPrice = statefulService1.order("userA", 10000);
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : 사용자 A 주문 금액 조회
//        int price = statefulService1.getPrice();

//        System.out.println("price = " + price); // -> 만원 나와야 하는데 이만원 나옴 -ㅇ-!!!
        System.out.println("userAPrice = " + userAPrice);

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        // -> statefulService1 이마넌 나옴

    } // statefulServiceSingleton()

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        } // statefulService()
    } // end class

} // end class