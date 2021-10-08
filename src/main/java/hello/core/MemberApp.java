package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args){
//        AppConfig appConfig=new AppConfig();
//       MemberService memberService=appConfig.memberService(); //app 개발에서 AppConfig사용!

        /**
         * appConfig의 환경설정 정보를 가지고, spring 컨테이너에 들어간 것들 (bean으로 등록한애들) 을 찾아와 쓸 수 있게됨
         */
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);//bean에서 꺼내올 이름(메소드가 기본 이름으로 등록됨), 타입
        Member member=new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);


        Member findMember=memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName()); //단축키 : soutv

    }
}
