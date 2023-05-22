package hello.hellospring.repository;
import hello.hellospring.Domain.Member;
import java.util.Optional;
import java.util.List;
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long Id);
    Optional<Member> findByName(String name);
    //지금까지 저장된, 회원을 리스트로 반환한다.
    List<Member> findAll();
}
