package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.sql.DataSourceDefinitions;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    /**
     * DiscountPolicy를 조회하면, 자식 타입이 2개 함께 딸려온다 -> 기본설정임.  (RateDiscountPoilcy, FixDiscoutnPolicy)
     * 그러면 자식의 중복오류가 발생한다!
     */
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);


    //중복 자식이랑, 타입으로 조회했을 때 중복 이름이 2개 (MemberRepository 예시) 랑 무슨 차이인지,,,,,
    @Test
    @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면 오류가 난다")
    void findBeanByParentTypeDuplicate(){
 //       DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
          assertThrows(NoUniqueBeanDefinitionException.class,
                ()->ac.getBean(DiscountPolicy.class));

    }

    @Test
    @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 빈 이름을 지정하여 찾아준다")
    void findBeanByParentTypeBeanName(){
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllByParentType(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String s : beansOfType.keySet()) {
            System.out.println("key = "+s+" value = "+beansOfType.get(s));  //실제 테스트케이스에서는 print문을 쓰면 안된다.

        }
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")  //좋은 방법은 아니다. (DiscountPolicy로 검색X, 더 하위인 RateDiscountPolicy로 검색하는것)
    void findBeanBySubType(){
        RateDiscountPolicy bean=ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    //자바는 모든 것의 상위가 Object이므로, 빈에 등록된 모든게 조회된다
    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllByObjectType(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = "+key+" value = "+beansOfType.get(key));

        }
    }

    /**
     * 두 Bean 모두 "DiscountPolicy", return 되는 Bean 객체가 "RateDiscountPolicy"와 "FixDiscountPolicy" 두 가지
     * 하나의 부모(DiscountPolicy)에 대해 중복되는 자식(RateDiscountPolicy)가 존재 -> 조회 시 오류!
     * 다형성의 측면에서 "인터페이스가 부모, 구현체가 자식" 이 된다.
     */

    @Configuration //해당 class를 빈에 설정정보로 등록하기 위한 notation
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
