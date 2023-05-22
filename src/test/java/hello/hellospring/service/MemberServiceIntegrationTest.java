package hello.hellospring.service;

import hello.hellospring.Domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional

class MemberServiceIntegrationTest {
    //기존 코드는 생성자 injection이 좋은데
    //Test코드는 다른데 가져다 쓸게 아니기 때문에 필드 인젝션 해주면 된다.
    //생성자 Injection을 해주었던 이유는 Spring띄울때만 객체를 지정하고, 이후에는 변경이 없게 하려고
    @Autowired MemberService memberService; // = new MemberService();
    @Autowired MemberRepository memberRepository; // = new MemoryMemberRepository();

    //기존에는 객체를 내가 직접 넣었다.
    //이제는 Spring Container 한테 MemberService, MemberRepository 내놔
    //테스트는 제일 끝단에 있는거기 때문에, 테스트는 제일 편한 방법을 쓰면 된다.

    //@Transactional 때문에, 이제 @AfterEach가 필요 없어졌다.

    @Test
    //@Commit
    public void 회원가입() throws Exception{
        //given : 무언가가 주어졌는데
        Member member = new Member();
        member.setName("Gangmin");

        //when : 이걸 실행했을때
        Long saveId = memberService.join(member);

        //then : 결과가 이게 나와야 돼
        Member findMember = memberRepository.findById(saveId).get();
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