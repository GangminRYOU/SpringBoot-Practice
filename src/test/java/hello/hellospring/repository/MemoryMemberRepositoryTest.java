package hello.hellospring.repository;

import hello.hellospring.Domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//class level에서 케이스들을 전부 같이 돌릴 수 있다.
//hello.hellospring에서 test를 돌리면, 전체를 한번에 돌릴 수 있다.
public class MemoryMemberRepositoryTest {
    //MemoryMemberRepository만 테스트할 거기 때문에, MemoryMemberRepository로
    //자료형을 바꾼다.
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //테스트가 끝날때마다, 리포지토리를 깔끔하게 지워주는 코드를 넣어야한다.
    @AfterEach
    //메서드가 끝날때 마다, 어떤 동작이 실행되도록하는 Annotation
    //CallBack 메서드
    public void afterEach(){
        repository.clearStore();
    }
    //Test가 실행이되고 끝날때마다, store를 한번씩 지운다.

    //JUnit의 api를 import해서 그냥 함수만 실행해볼 수 있다.
    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring"); // ctrl + shift + enter하면 바로 내려온다.

        repository.save(member);
        //optional에서 반환값을 꺼낼때는 .get()으로 꺼낼 수 있다.
        Member result = repository.findById(member.getId()).get();
        //검증은 new해서 DB에 넣은거랑, DB에서 findById랑 똑같으면, 정상작동
        //단순히 이렇게 check할 수도 있다.
        System.out.println("result = " + (member == result));
        //그런데 내가 글자로 계속 확인해가면서 보기는 힘들다.
        //그래서 Assert라는 기능이 있다.
        Assertions.assertEquals(member, result);
        //요즘에는 이걸 많이 쓴다.
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        //복사할때 이름이 다른 경우, shift + F6으로 이름을 자동 변환할 수 있다.
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member member3 = new Member();
        member3.setName("spring3");
        repository.save(member3);
        //.get()을 통해 반환값이 Optional인 경우 까서 꺼낼 수 있다.
        Member result2 = repository.findByName("spring1").get();
        assertThat(member1).isEqualTo(result2);
        //spring2를 이름으로 찾으면 어떻게 될까?
        //Member result2 = repository.findByName("spring2").get();
        //assertThat(member1).isEqualTo(result2);
        //다른 객체다라고 뜬다.
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring5");
        repository.save(member2);
        Member member3 = new Member();
        member3.setName("spring6");
        repository.save(member3);
        Member member4 = new Member();
        member4.setName("spring7");
        repository.save(member4);
        List<Member> newList = repository.findAll();
        assertThat(newList.size()).isEqualTo(4);
    }


}
