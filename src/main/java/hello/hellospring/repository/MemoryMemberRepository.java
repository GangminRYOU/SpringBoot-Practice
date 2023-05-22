package hello.hellospring.repository;

import hello.hellospring.Domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    //save해서 저장할 리포지토리 : Key는 id, 값은 Member로 저장한다.
    private static Map<Long, Member> store = new HashMap<>();
    //실무에서는 동시성 문제가 있어서 공유되는 변수일떄는 Concurrent Hash map을 써야한다.

    private static long sequence = 0L;
    //이 부분도 save할떄 같이 올라가기 때문에, 동시성 문제가 발생가능하다.
    //name은 고객이 적어서 넘어온다.
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return (member);
    }

    @Override
    public Optional<Member> findById(Long Id) {
        return Optional.ofNullable(store.get(Id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member->member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    //store를 싹 비운다.
    public void clearStore(){
        store.clear();
    }
}
