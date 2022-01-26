package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {
    //싱글톤과 프로토타입빈을 함께 사용할 경우에 대한 예제1 (count=0 이고, 서로 다른 두 명의 클라이언트가 count를 증가시킬 때)

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

        //prototype bean으로 서로 빈을 각각 요청한 후, addCount했기 때문에 prototypeBean1과 prototypeBean2는 둘 다 테스트를 통과해야함 (각각 0->1, 0->1)
    }

//    클라이언트 빈 (singleton bean) 내에 prototypebean이 존재할 때의 문제점 테스트
    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class,ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1=clientBean1.logic();
        assertThat(count1).isEqualTo(1);


        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2=clientBean2.logic();
        assertThat(count2).isEqualTo(2); // 싱글톤인 클라이언트 빈 내에서 관리되는 prototype Bean -> 이미 주입이 끝난 애 이므로 프로토타입 빈이어도
        // 하나의 clientBean을 통해 호출할 경우, 새로운 인스턴스가 아닌 동일한 인스턴스 반환 -> count가 1이 아닌 2가 된다!
    }


    @Scope("singleton") //원래 빈은 디폴트가 singleton이므로, 원래는 scope("") 를 굳이 작성해주지 않아도 된다.
//    @RequiredArgsConstructor // autowired를 사용해서 prototypeBean을 초기화 해주지 않아도 자동으로 자기가 넣어줌
    static class ClientBean{
        private final PrototypeBean prototypeBean; //이 prototypeBean은 clientBean이 생성되는 시점에 주입이 완료되어 버린다! 
        
        //이걸 해결하는 멍청한 방법: logic method 내부에서 매번 새롭게 ac.getBean(PrototypeBean.class)로 새롭게 요청, 새롭게 인스턴스를 받는것

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        //클라이언트가 ClientBean을 통해 prototypeBean의 count++를 시켜주는 메서드
        public int logic(){
//            PrototypeBena prototypeBean = ac.getBean(PrototypeBean.class);
//            이 방법은 의존성 주입 (Dependency Injection = DI )가 아니라 직접 필요한 의존관계를 찾는 의존과꼐 조회 (탐색) (Dependency Lookup = DL )이라 한다.
//            이런 DL 기능을 해주는게 스프링에 따로 존재한다!

            prototypeBean.addCount();
            int count=prototypeBean.getCount();
            return count;
            //ctrl+alt+N -> 위의 두 라인으로 쪼개선 int count= ; 과 return을 한 라인으로 합쳐준다. (인라인으로)
        }



    }
    @Scope("prototype")
    static class PrototypeBean{
        private int count=0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init "+this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
