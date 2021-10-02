package hello.core.member;

/**
 * 사용자가 실제 마주하게 되는 implement
 */
public class MemberServiceImpl implements MemberService{
    
    private final MemberRepository memberRepository=new MemoryMemberRepository(); //interface가 실체 구현체 참조가능
    
    @Override
    public void join(Member member) {
        memberRepository.save(member); //다형성에 의해 실구현체의 오버라이딩 된 메소드 실행
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
