package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component // MyLogger는 컨테이너에 등록할 빈임
@Scope(value = "request") //HTTP 요청당 빈 생성
//scope= request -> 고객 요청이 온 순간 ~ 빠져나가는 순간까지가 생존범위
//그래서 request가 없으면, MyLogger도 컨테이너에 등록되지 않는다!!
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL){
        this.requestURL=requestURL;
    }

    public void log(String message){
        System.out.println("["+uuid+"]"+"["+requestURL+"]"+" ["+message+"]");
    }

    @PostConstruct
    public void init(){
        //빈 생성 시 바로 init() 호출
        //완전 고유한 값 생성
        this.uuid= UUID.randomUUID().toString();
        System.out.println("["+uuid+"] request scope bean create: "+this); //this에는 생성된 애의 주소가 출력됨
    }

    @PreDestroy
    public void close(){
        //고객 요청이 서버에서 빠져나갈 때 destroy 호출
        System.out.println("["+uuid+"] request scope bean close: "+this);
    }
}
