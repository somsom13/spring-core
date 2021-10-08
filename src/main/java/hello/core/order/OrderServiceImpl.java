package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; // 인터페이스에만 의존하게 코드 수정한 것! <- 누군가 외부에서 대신 여기에 구현체를 주입해주어야 한다
    
    //private final DiscountPolicy discountPolicy=new RateDiscountPolicy();
    // 초기코드: 여기서 참조부분만 변경해주면, 할인정책에 따라 바꿔줄 수 있다!

    /**
     *But, OrderServiceImple은 인터페이스인 DiscountPolicy에만 의존해야 하는데, 사실 알고보면 구체 클래스인 RateDiscountPolicy도 의존하고 있게 된다!
     * 추상과 구체 모두에 의존 ? -> DIP를 위반하는 것. (DIP: 구체가 아닌 추상, 즉 인터페이스에 의존하라는 것) -> 문제!
     * OrderServiceImpl의 구체 관련 소스코드를 변경해야 한다는 것? -> OCP 위반!!! (기능 변경이 클라이언트 코드에 영향을 미치므로)
     * */

    //생성자 주입 -> 객체 주입  (OrderServiceImple은 어떤 구현체가 들어올지 전혀 몰라야 한다)
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        //가격 관련 부분은 디른곳에서 다 결정해주기 때문에 설계가 아주 잘 된 형태임
        Member member=memberRepository.findById(memberId);
        int discountPrice=discountPolicy.discount(member,itemPrice);//discount에서 member의 레벨로 할인 여부 판단
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
