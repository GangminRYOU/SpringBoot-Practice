package hello.hellospring.repository;

import hello.hellospring.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JPA는 JPA Repository라는 것을 받아야한다.
//interface가 interface라는 것을 받을 때는 extends
//interface는 다중 상속이 된다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
    //이러면 끝난다.
}
