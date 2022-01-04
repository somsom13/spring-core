package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component //component scan에 의해 rateDiscountPolicy라는 이름으로 (deafult값) bean으로 등록된다!
//@Qualifier("mainDiscountPolicy")
@Primary
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent=10;
    @Override
    public int discount(Member member, int price) { //여기서 ctrl+shift+t => 자동으로 test 생성
        if(member.getGrade()== Grade.VIP){
            return price*discountPercent/100;
        }else{
            return 0;
        }
    }
}
