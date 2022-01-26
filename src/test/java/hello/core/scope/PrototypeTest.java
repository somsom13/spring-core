package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {



    @Test
    void prototypeBeanFind(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1=ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close(); //얘는 close해서 컨테이너를 종료시켜도 destroy가 호출되지 않는다. (의존성 주입 등 이후에 관리 X -> 종료메세지 호출 안됨)

        //destroy를 호출하려면? 클라이언트가 수동으로 호출! (적절히 타이밍 잡아서)
//        prototypeBean1.destroy();
//        prototypeBean2.destroy();

    }


    //여기에 @Component annotation을 달지 않아도, 위에서 annocationContext에 해당 빈 클래스를 명시해주면 컴포넌트 스캔 대상자로서 바로 들어간다.
    //prototype bean을 생성한다고 명시 (테스트에 사용할 빈)
    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
