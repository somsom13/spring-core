package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * 총 관리자! 모든 배역 할당은 여기에서 이루어진다.
 * 이런식으로 AppConfig에서 객체 생성, 생성자로 전달하는 것을 memberServiceImpl 입장에서는 '의존관계를 주입해주는 것 같다' 라고 하여
 * "DI" (Dependency Injection) => 의존관계 주입 이라 한다!
 */
public class AppConfig {

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository()); //여기서 생성자로 넘겨준다  => "생성자 주입"
    }

    private MemberRepository memberRepository() { //역할이 드러나게 extract Method : 어플리케이션 전체 구성을 빠르게 파악할 수 있음
        return new MemoryMemberRepository();
    }

    public OrderService orderservice(){
        return new OrderServiceImpl(memberRepository(), getDiscountPolicy()); //객체 2개 주입 생성자로!
    }

    private FixDiscountPolicy getDiscountPolicy() {
        return new FixDiscountPolicy();
    }

}
