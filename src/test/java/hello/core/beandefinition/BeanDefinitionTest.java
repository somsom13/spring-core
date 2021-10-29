package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
//    GenericXmlApplicationContext ac=new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition=ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){
                //appconfig에 직접 등록한 애들만 sout으로 출력
                System.out.println("beanDefinitions = "+beanDefinitionName+" beanDefinition = "+beanDefinition);
                //등록된 빈 이름과 관련 beanDefinition 자체를 출력해서 확인
            }

        }
    }
}
