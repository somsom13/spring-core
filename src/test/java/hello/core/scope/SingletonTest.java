package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    void singletonBeanFind(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class); //클래스 명시 -> 자동으로 component scan으로 등록

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2=ac.getBean(SingletonBean.class);
        System.out.println("singletonBean2 = " + singletonBean2);
        System.out.println("singletonBean1 = " + singletonBean1);
        assertThat(singletonBean1).isSameAs(singletonBean2);

        /**
         * test 성공! (singleton은 아무리 여러번 요청해도, 매번 같은 빈을 반환해준다
         */
        ac.close();
    }

    //테스트용 빈 클래스 생성
    @Scope("singleton") //원래는 디폴트가 싱글톤이기 때문에 명시할 필요 없음
    static class SingletonBean{
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }
}
