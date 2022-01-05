package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 빈 생명주기 테스트 위해 생성한 부분
 */
public class NetworkClient{
    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = "+url);
//        connect();//객체 생성 시 connect
//        call("초기화 연결 메세지");
    }

    public void setUrl(String url){
        this.url=url;
    }

    //서비스 시작 시 호출
    public void connect(){
        System.out.println("connect: "+url);
    }

    //서비스가 열려있을 때 호출 가능
    public void call(String message){
        System.out.println("call: "+url+" message = "+message);
    }

    //서비스 종료 시 호출 -> 안전한 서비스 연결 종료를 위한 것
    public void disconnect(){
        System.out.println("close "+ url);
    }

//    @Override //InitializingBean을 implement => 초기화 콜백
//    public void afterPropertiesSet() throws Exception{
//        //property setting이 끝나면, 즉 의존관계 주입이 끝나면 호출해주겠다는것!
//        connect();//객체 생성 시 connect
//        call("초기화 연결 메세지");
//    }
//
//    @Override //DisposableBean을 implement => 소멸전콜백
//    public void destroy() throws Exception {
//        disconnect(); //bean종료돌 때 연결 안전하게 종료
//    }

    //초기화, 소멸 메소드 사용
    //Bean 등록할 때 얘네를 initMethod, destroy Method로 지정해준다
    //어노테이션으로 초기화, 소멸 콜백을 사용하려면? => 초기화 / 소멸 메소드에 annotation만 추가해주면 된다!

    @PostConstruct
    public void init(){
        connect();//객체 생성 시 connect
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close(){
        disconnect();
    }
}
