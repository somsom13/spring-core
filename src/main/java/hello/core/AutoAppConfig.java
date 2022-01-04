package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//자동으로 빈으로 등록해준다. (@Bean annotation 없이)
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes=Configuration.class)
        /*@Component annotation이 붙은건 다 컨테이너에 등록하겠다!
        그런데 그러면 @Configuration도 그 내부구조를 보면, 위에 @Component annotation이 붙어있음
         따라서 @Configuration이 붙은 AppConfig 같은 것도 component 로써 등록되어버린다.
        그런 애들은 빼주기 위해 @Configuration이 붙은 애들은 등록대상(component scan 대상)에서 제외하도록 필더를 씌워주는 것*/
)
public class AutoAppConfig {
    //여기에 수동으로 @Bean으로 등록할 수도 있다. 수동으로 등록한애와 자동등록된 (component scan된) 애가 충돌된다면?
    // -> 수동빈이 오버라이드 / 스프링부트로 돌리면? 에러발생

    //수동 bean 등록 예제
//    @Bean(name="memoryMemberRepository")
//    MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }

}
