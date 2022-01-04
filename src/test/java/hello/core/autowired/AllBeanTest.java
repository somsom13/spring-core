package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    //같은 타입으로 등록된 모든 빈을 확인, 동적으로 사용해야 할 때
    @Test
    void findAllBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        
        Member member=new Member(1L,"userA", Grade.VIP);
        int discountPrice=discountService.discount(member,10000,"fixDiscountPolicy");

        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(discountPrice).isEqualTo(1000);

    }


    @RequiredArgsConstructor
    static class DiscountService{
        //테스트에 사용할 서비스 임의 생성
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        public int discount(Member member, int i, String discountCode) {
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
            DiscountPolicy discountPolicy=policyMap.get(discountCode); //map에 <FixDiscountPolicy,등록된빈><RateDiscountPolicy,등록된빈> 이런느낌
            int discountPrice=discountPolicy.discount(member,i);
            return discountPrice;
        }
        //AutoAppConfig에 의해서 component scan, FixPolicy와 RatePolicy가 둘 다 bean에 등록 -> map과 list에 두 개가 다 출력된다.




    }
}
