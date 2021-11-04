package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 사용자가 실제 마주하게 되는 implement
 */
@Component("memberServiceImplName") //default로는 앞글자만 소문자로 바꾼 classname이 등록된다.
public class MemberServiceImpl implements MemberService{
    
    private final MemberRepository memberRepository;

    //생성자를 통해서 memberRepository 객체 구현체 설정
    @Autowired  // 생성자에 @Autowired를 붙여주면, 스프링 컨테이너에서 생성자를 보고 여기에 맞는 애를 컨테이너에서 가져와 자동으로 주입해준다.
    // (여기서 요구하는 memberRepository와 타입이 맞는애를 찾아 가져온다! -> 자식타입인 MemoryMemberRepository 주입함)
    public MemberServiceImpl(MemberRepository memberRepository) { //생성자로 넘어오는 값으로 MemberRepository에 MemoryMemberRepository 주입가능!!
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member); //다형성에 의해 실구현체의 오버라이딩 된 메소드 실행
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    //테스트용 (MemberRepository가 두 번 호출되어 싱글톤이 깨지는것인가? 에 대한
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
