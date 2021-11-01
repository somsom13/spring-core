package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService=ac.getBean("memberService", MemberServiceImpl.class); //원래는 구체 타입으로 꺼내면 안좋음
        OrderServiceImpl orderService=ac.getBean("orderService",OrderServiceImpl.class);
        MemberRepository memberRepository=ac.getBean("memberRepository",MemberRepository.class);

        MemberRepository memberRepository1= memberService.getMemberRepository();
        MemberRepository memberRepository2= orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = "+memberRepository1);
        System.out.println("orderService -> memberRepository = "+memberRepository2);
        System.out.println("MemberRepository in Bean = "+memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        /**
         * spring container에 등록되어있는 memberRepository와
         * 다른 service의 빈 등록으로 인해 new로 호출되는 MemberRepository들은 모두 "같은 MemberRepository 객체이다"
         *
         * => 매번 new로 호출해도 "memberRepository"는 처음 빈에 등록될 때 한 번만 메소드가 호출된다!
         */

    }


    @Test
    void configurationDeep(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class); //AppConfig자체도 컨테이너에 bean으로 등록된다.

        System.out.println("bean = "+bean.getClass());
        //내가 만든 클래스가 아니라, 스프링에서 다른 클래스를 생성해서 자체적으로 등록한것! (내가 만든 AppConfig를 상속한 조작 class)
        //그 조작 class에서 이미 존재하는 bean이면, 컨테이너에서 찾아서 반환하고 , 없으면 새로 생성해서 등록시켜주는 로직일 것임.
        //이게 @Configuration에 의해서 가능해지는 것!
        //@Configuration이 없다면? 그 조작 클래스가 아닌 내가 만든 순수 class가 들어가지기 때문에 singleton이 깨진다.
    }
}
