package hello.hellospring.service;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.Domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    //회원 서비스를 만드려면, 회원 리포지토리가 있어야 한다.
    private final MemberRepository memberRepository; // = new MemoryMemberRepository();

    //MemberRepository를 Class가 직접 new해서 생성하는게 아니라, 외부에서 넣어주도록 변경
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입 메서드
    public Long join(Member member){
     //   long start = System.currentTimeMillis();
      // try {
           ValidateDuplicateMember(member);    //중복 회원 검증
           //이런식으로 findByName의 결과는 optional member니까, 바로 .ifPresent로 optional method수행
           //이런 로직을 가진 함수는 메서드로 뽑는게 좋다. 단축키: ctrl + shift + alt + t
           memberRepository.save(member);
           //임의로 Id반환
           return (member.getId());
     //  }finally {
           //로직이 끝날때 예외가 터져도 찍는다.
     //      long end = System.currentTimeMillis();
     ///     System.out.println("join" + (end - start) + "ms");
      // }
    }

    private void ValidateDuplicateMember(Member member) {
        //이미 이름이 있는 회원은 가입이 불가하다는 비즈니스 로직
        //ctrl + alt + v = 자동으로 리턴 값에 맞는 변수 생성
        memberRepository.findByName(member.getName())
                        .ifPresent(m->{
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
        //ifPresent : null이 아닌 어떤 값이 있으면,동작 -> optional이기 떄문에 가능
        //기존에는 if (!null)으로 받음
        //optional로 감싸면, optional안에 멤버 객체가 있다.
        //그래서, optional을 통해 여러 메서드를 쓸 수 있다. -> ifPresent같은 메서드
        //optional을 꺼내고 싶으면 그냥 result.get으로 꺼내면 된다.
        //Member member1 = result.get();
        //그러나, 직접 바로 꺼내는 것을 권장하지 않는다.
        //추가적으로 orElseGet 이라는 optional method가 있다.
        //result.orElseGet();
        //값이 있으면 꺼내고, 값이 없으면 default로 설정된 값으로 method를 실행해
        //Optional로 바로 반환하는게 별로 좋지가 않다. -> 일단 안 이쁨
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        //repository의 메서드이름은 단순히 저장소에 뺐다 넣었다 하는 느낌이 든다.
        //서비스 클래스는 naming이 비즈니스에 가깝다.
        //서비스 클래스의 메서드는 비즈니스에 가까운 용어를 써야한다.
        //그래야 개발자든 기획자든 매칭이 된다.
        //서비스쪽은 비즈니스 의존, repository는 개발쪽으로 네이밍을 한다.
        //long start = System.currentTimeMillis();
        //try{
            return (memberRepository.findAll());
        //}finally {
        //    long end = System.currentTimeMillis();
        //    System.out.println("findMembers = " + (end - start) + "ms");
        ///}
    }
    //Id로 회원 찾기
    public Optional<Member> findOne(Long memberId){
        return (memberRepository.findById(memberId));
    }
}
