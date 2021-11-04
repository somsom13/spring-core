package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class); //name이 beanA 인 이유? -> ComponentScan으로 등록된 애들은 className의 앞글자를 소문자로 바꾸어 이름으로 등록!
        assertThat(beanA).isNotNull();
        //BeanA는 class위에 @MyIncludeComponent라고 annotaion을 달았고, 해당 annotaion은 includeFilters -> ComponentScan의 대상이 된다!


        assertThrows(
                NoSuchBeanDefinitionException.class,
                ()->ac.getBean("beanB",BeanB.class));

        //beanB는 class위에 @MyExcludeComponent annotation -> 이는 excludeFilters로 지정 => ComponentScan 대상에서 제외된다!
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type= FilterType.ANNOTATION,classes=MyIncludeComponent.class),
            excludeFilters = @Filter(type=FilterType.ANNOTATION,classes=MyExcludeComponent.class))
    //FilterType.Annotation: 기본값 (Annotation에 대해 필터링을 한다는 것.) 이 외에도 다양한 filterType 옵션
    static class ComponentFilterAppConfig{

    }
}
