package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceImpleTest {

    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository=new MemoryMemberRepository();
        memberRepository.save(new Member(1L,"name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy()); //만약 orderServiceImpl에서 생성자 주입이 아닌 set(수정자 주입)을 사용한다면?
        //위처럼 new로 객체를 생성해도 수정자가 호출되지 않았기 때문에 memberRepository, discountRepository에 값이 주입되지 않음 -> null point exception 발생
        // -> 생성자 주입을 사용해라!
        Order order=orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
