package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final 키워드 붙은 것들로 생성자 자동 생성!
public class OrderServiceImpl implements OrderService{

    /**
     * 인터페이스에만 의존, 구현체 주입: 하단의 생성자에서! (즉 외부에서 생성자를 통해 주입해주는 것)
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; // 인터페이스에만 의존하게 코드 수정한 것! <- 누군가 외부에서 대신 여기에 구현체를 주입해주어야 한다
    
    //private final DiscountPolicy discountPolicy=new RateDiscountPolicy();
    // 초기코드: 여기서 참조부분만 변경해주면, 할인정책에 따라 바꿔줄 수 있다!

    /**
     *But, OrderServiceImple은 인터페이스인 DiscountPolicy에만 의존해야 하는데, 사실 알고보면 구체 클래스인 RateDiscountPolicy도 의존하고 있게 된다!
     * 추상과 구체 모두에 의존 ? -> DIP를 위반하는 것. (DIP: 구체가 아닌 추상, 즉 인터페이스에 의존하라는 것) -> 문제!
     * OrderServiceImpl의 구체 관련 소스코드를 변경해야 한다는 것? -> OCP 위반!!! (기능 변경이 클라이언트 코드 (여기서는 OrderServiceImpl)에 영향을 미치므로)
     * */

    //생성자 주입 -> 객체 주입  (OrderServiceImpl은 어떤 구현체가 들어올지 전혀 몰라야 한다)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
//        this.discountPolicy = rateDiscountPolicy;
        //DiscountPolicy라는 타입에 대해 fixDiscountPolicy, rateDiscountPolicy 두 개가 등록되어 에러 발생하는 경우 대비 -> 이름을 rateDiscountPolicy로 명시해준다.

        //Qualifier 를 사용하는 경우: 파라미터에 qualifier를 명시!
        this.discountPolicy=discountPolicy;
        //Qualifier는 등록대상의 위에 annoation으로 명시

        //Primary annotation으로 우선순위를 부여하는게 제일 간편하다. (qualifier와 primary를 둘 다 붙인다면? qualifier (더 상세하게 지정함)이 더 우선순위가 높다)
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //가격 관련 부분은 디른곳에서 다 결정해주기 때문에 설계가 아주 잘 된 형태임
        Member member=memberRepository.findById(memberId);
        int discountPrice=discountPolicy.discount(member,itemPrice);//discount에서 member의 레벨로 할인 여부 판단
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }


    //memoryMemberRepository 싱글톤 테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
