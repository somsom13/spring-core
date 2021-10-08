package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 10% 할인")
    void vip_o(){
        Member member=new Member(1L,"memberVIP", Grade.VIP);

        int discount= rateDiscountPolicy.discount(member,10000);

        assertThat(discount).isEqualTo(1000); //Assertions는 static으로 쓰는게 좋다!
    }

    @Test
    @DisplayName("VIP가 아니면 10% 할인이 적용되면 안된다.")
    void vip_x(){
        Member member=new Member(2L,"memberBASIC", Grade.BASIC);

        int discount= rateDiscountPolicy.discount(member,10000);

        assertThat(discount).isEqualTo(0);
    }
}