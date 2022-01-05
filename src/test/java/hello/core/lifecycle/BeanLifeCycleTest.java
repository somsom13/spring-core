package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); //원래 context를 닫아주어야함 (근데 보통은 close 할 일이 많지 않음 / close하려면 Configurable로 변수타입 변경)
    }

    //테스트에 사용할 configuration 정보 -> bean 등록
    @Configuration
    static class LifeCycleConfig{

        //호출 결과물, 즉 return 값이 bean에 등록된다.
//        @Bean(initMethod="init",destroyMethod = "close")//등록할 객체 (NetworkClient의 초기화, 소멸 메소드 지정)
        @Bean //annotation 사용할 경우, 별도로 메소드 지정 필요 없음
        public NetworkClient networkClient(){
            NetworkClient networkClient=new NetworkClient(); //url이 없는 상태로 객체가 생성됨
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
