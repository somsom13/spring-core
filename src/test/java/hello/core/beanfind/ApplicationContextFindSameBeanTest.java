package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextFindSameBeanTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 이름이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate(){
        //MemberRepository bean = ac.getBean(MemberRepository.class);//타입만 지정해주는 것!
        assertThrows(NoUniqueBeanDefinitionException.class,
                ()->ac.getBean(MemberRepository.class));
    }


    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상이면, 빈 이름을 지정해주면 된다")
    void findBeanByName(){
        MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository1).isInstanceOf(MemberRepository.class);
    }


    @Test
    @DisplayName("특정 타입을 모두 조회하기") //ctrl+shift+enter => 자동 줄 변경
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key="+key+" value= "+beansOfType.get(key));
            //key: 저장된 이름 (메소드 이름), value: 등록된 빈 객체
        }

        System.out.println("beansOfType= "+beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    //여기에서만 사용하기 위해 static으로 선언한 class, 테스트용
    /**
     *
     * 두 Bean 모두 return값 (컨테이너에 Bean 객체로 등록되는 것) 이 "MemoryMemberRepository" -> 자식은 한 타입이다! (자식 타입 중복오류X)
     * 한 가지 부모타입(MemberRepository)에 대해 두 가지 Bean이 호출 -> 같은 타입의 중복오류
     */

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() { //객체 이름이 같아도 return값이 다를 수 있다. (보통 return되는 값의 파라미터가 바뀜)
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
