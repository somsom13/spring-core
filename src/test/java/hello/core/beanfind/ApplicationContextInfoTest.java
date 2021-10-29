package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    /**
     * 등록된 모든 빈 조회하기
     */
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        //iter + tab -> 자동 iteration 완성
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean=ac.getBean(beanDefinitionName);
            System.out.println("name= "+beanDefinitionName+" object: "+bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean(){
        String[] beanDefinitionNames=ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);  //메소드 먼저 호출하고 저장할 변수 자동선언 -> ctrl+alt+v

            if (beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){ //ROLE_INFRASTRUCTURE: 빈 내부에서 사용하는 것들
                //어플리케이션 개발을 위해 등록한 빈들만 true
                Object bean=ac.getBean(beanDefinitionName);
                System.out.println("name= "+beanDefinitionName+" object= "+bean);
            }
        }
    }
}
