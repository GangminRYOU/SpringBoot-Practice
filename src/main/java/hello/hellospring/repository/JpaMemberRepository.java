package hello.hellospring.repository;

import hello.hellospring.Domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //JPA는 EntityManager라는 걸로 모든게 돌아간다.
    //우리가 data-jpa 라이브러리를 받아 놓으면, Spring이 자동으로 EntityManger라는 것을 생성을 해준다.
    //현재 DB와 연결까지 해서 EntityManager를 만들어 줌
    //우리는 그냥 Injection받으면 된다.
    //properties세팅 정보, DB커넥션 정보등 많은 정보들을 다 자동으로 짬뽕해서 만들어준다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em){
        this.em = em;
    }
    @Override
    public Member save(Member member) {
        em.persist(member);
        //persist는 영속화하다는 뜻, 영구 저장하다
        return member;
    }

    @Override
    public Optional<Member> findById(Long Id) {
       Member member = em.find(Member.class, Id);
       //조회할 타입과 식별자 PK값 넣어주면, 조회가 된다.
       return Optional.ofNullable(member);
       //값이 null일수도 있으니, 이렇게 해주면 된다.
    }

    //PK는 위처럼 하면 되는데, name은 특별한 JPK라는 객체지향 쿼리 언어를 써야 한다.
    //거의 SQL과 똑같다.
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
               .getResultList();
    }
}
