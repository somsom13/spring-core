package hello.core.member;

/**
 * 사용자가 실제 마주하게 되는 implement
 */
public class MemberServiceImpl implements MemberService{
    
    private final MemberRepository memberRepository;

    //생성자를 통해서 memberRepository 객체 구현체 설정
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
}
