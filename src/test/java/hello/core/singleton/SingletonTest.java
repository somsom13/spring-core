package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer(){
        //스프링 없는 순수 DI 이기 때문에 ApplicationContext (컨테이너) 에서 가져오지 않고 바로 새 AppConfig 만들어서 테스트
        AppConfig appConfig=new AppConfig();

        //1. 조회 : 호출 시 마다 객체 생성
        MemberService memberService1=appConfig.memberService();

        //2. 조회: 마찬가지로 다른 호출 들어오면 객체 생성 -> 1과 다른 memberService 객체가 생성된다 => 메모리에 쌓인다
        //요청이 계속 들어오는 웹 어플리케이션에서는 안좋다!
        MemberService memberService2=appConfig.memberService();

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    /**
     * 객체가 하나만 생성되고, 해당 객체가 공유되게 설계해야 한다 => "싱글톤 패턴"
     */
    
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        assertThat(instance1).isSameAs(instance2);
        //same: ==비교
        //equal: java의 equals 비교
    }

    /**
     * 스프링 컨테이너를 사용하면: 스프링 컨테이너 (=싱글톤 컨테이너) 내에서 알아서 관리해준다!
     * 빈 이름(메소드네임)-빈 객체 를 저장소에 저장해둔 후, 조회 요청이 올 때 마다 거기에서 꺼내서 반환해준다
     */

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContianer() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        assertThat(memberService1).isSameAs(memberService2);
    }
}
