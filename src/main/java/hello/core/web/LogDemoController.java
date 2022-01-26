package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor // 자동으로 여기서 필요한 빈을 찾아와 연결해줌
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger; //Required어쩌구로 자동으로 연결됨 -> MyLogger의 scope가 request면, MyLogger가 등록되어 있지 않으므로 DI 에서 오류발생

//    private final ObjectProvider<MyLogger> myLoggerProvider; // MyLogger가 생성 시 DI 되는 것 X, MyLogger를 컨테이너에서 찾을 수 있는 대리자가 만들어지는것!
    @RequestMapping("log-demo")
    @ResponseBody //뷰 템플릿 없이 바로 응답을 반환함
    public String logDemo(HttpServletRequest request) throws InterruptedException {
//        MyLogger myLogger = myLoggerProvider.getObject(); //provider를 사용하면, http 요청이 온 상태에서만 getObject가 이루어지기 때문에 오류 없이 실행시킬 수 있따
        String requestURL=request.getRequestURL().toString(); // 고객이 어떤 url로 요청했는지 알 수 있다

        System.out.println("myLogger = " + myLogger.getClass()); // spring이 조작한 MyLogger가 출력됨 -> 가짜 프록시 객체를 불러둔 후, 가짜 메소드 -> 진짜 메소드를 호출하는 방식으로 동작한다.
        // 가짜 프록시 객체는 원본을 상속한 형태

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000); // 여러개 요청이 동시 다발적으로 들어오면, 여러 Uuid로 controller test가 찍히고 이후에 (sleep 때문에 1초 기다리므로) 나머지 testId들이 제대로 매칭된 uuid로 찍히는걸 볼 수 있다
        logDemoService.logic("testId");
        return "OK";
    }
}
