package hello.core;

import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 총 관리자! 모든 배역 할당은 여기에서 이루어진다.    -> 로미오 역할을 맡을 배우의 캐스팅은 이곳에서 진행한다.
 * 이런식으로 AppConfig에서 객체 생성, 생성자로 전달하는 것을 memberServiceImpl 입장에서는 '의존관계를 주입해주는 것 같다' 라고 하여
 * "DI" (Dependency Injection) => 의존관계 주입 이라 한다!
 */

@Configuration   //AppConfig을 설정정보로 사용하기 위해 붙이는 annotation
public class AppConfig {

    //이제 스프링 컨테이너에서 spring bean을 찾아 관리!

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()  : MemoryMemberRepository 두 번 호출 즉 두 번 생성 -> 싱글톤이 깨지는건?
    //테스트 결과: memberRepository 메소드는 최초 한 번만 호출된다! (즉 한 번 컨테이너에 등록되고 나면 다른 곳에서 new로 호출해도 메소드가 실행되지 않는다)
    
    @Bean    //각 메소드를 Spring Container에 (객체로) 등록! -> 등록된 각 객체를 "Spring Bean"이라 한다.
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService"); //단축키 soutm
        return new MemberServiceImpl(memberRepository()); //여기서 생성자로 넘겨준다  => "생성자 주입"
    }

    @Bean
    public MemberRepository memberRepository() { //역할이 드러나게 extract Method : 어플리케이션 전체 구성을 빠르게 파악할 수 있음
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), getDiscountPolicy()); //객체 2개 주입 생성자로!
    }

    @Bean
    public RateDiscountPolicy getDiscountPolicy() {
        System.out.println("call AppConfig.getDiscountPolicy");
        return new RateDiscountPolicy();//discountPolicy가 바뀌면, 이 부분만 변경해주면 된다!
    }

}
