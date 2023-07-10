package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 회원 찾기
        Member member = memberRepository.findById(memberId);
        // 할인 정책에 회원 넘기기
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 주문 생성
        return new Order(memberId, itemName, itemPrice, discountPrice);

        // 1. 주문 생성 요청이 오면, 회원 정보를 먼저 조회를 함 -> 그 다음 할인 정책에 회원을 그냥 넘겨버림!
        // 2. 회원과 itemPrice를 넘기고 최종적으로 할인된 가격(discountPrice)을 받아옴!
        // 3. 그리고 최종 생성된 주문을 반환함!

    } // createOrder

} // end class
