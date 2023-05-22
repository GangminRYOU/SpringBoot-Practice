package hello.hellospring.service;

import hello.hellospring.Domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService; // = new MemberService();
    //여기서도 clear를 통해 테스트간 의존관계를 없애 줘야한다.
    MemoryMemberRepository memberRepository; // = new MemoryMemberRepository();

    //memberService객체를 만들떄 MemoryMemberRepository를 내가 직접 넣어준다.
    //테스트가 동작하기 전에 넣는다.
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    //테스트를 실행할 떄 마다, 각각 Service와 Repository 객체를 생성해준다.
    //각 테스트를 실행하기 전에 MemoryMemberRepository객체를 만들고
    //그 객체를 MemberService객체의 생성자 매개변수로 넣어준다.
    //이렇게 되면, memberService객체와 memberRepository객체가 사용하는 Map이 같은
    //자료구조를 사용한다.
    //MemberService입장에서 보면,
    //MemberService에서 사용하는 MemoryMemberRepository는 직접 생성하지 않는다.
    //외부에서 MemoryMemberRepository를 넣어준다.
    //이런 걸 Dependency Injection, 즉 의존성 주입이라고 한다.
    @AfterEach
    public void clearStore(){
        memberRepository.clearStore();
    }
    //참고로 shift + F10하면, 이전에 실행했던 것을 그대로 실행해준다.
    @Test
    void 회원가입() {
        //given : 무언가가 주어졌는데
        Member member = new Member();
        member.setName("Gangmin");

        //when : 이걸 실행했을때
        Long saveId = memberService.join(member);

        //then : 결과가 이게 나와야 돼
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        //static import : alt + enter
    }
    //Test는 정상 Flow보다 예외 Flow가 훨씬 더 중요하다.
    //위의 테스트는 반쪽 짜리 테스트, join의 핵심은 중복회원 검증로직을 잘 파서
    //예외가 터트려지는지도 봐야한다.
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("Gangmin");
        Member member2 = new Member();
        member2.setName("Gangmin");
        //when
        memberService.join(member1);
        //방법 1
       /* try{
            memberService.join(member2);
            //예외가 안 터지고 실행이 되면 실패
            fail();
        }catch (IllegalStateException e){
            //Exception이 터지면, catch로 온다.
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //근데 이것 때문에, try catch넣는게 되게 애매하다.
        //방법 2
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //() -> memberService.join(member2)이 로직을 태울떄, IllegalStateException.class 예외가 터져야한다.
        //이 메서드는 반환이 되기 때문에, 메세지를 받을 수 있다.
        //assertThrows(NullPointerException.class, () -> memberService.join(member2));
        //type thrown ==> expected: <java.lang.NullPointerException> but was: <java.lang.IllegalStateException>
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}