package hello.hellospring.repository;

import hello.hellospring.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    //JDBC Template이라는게 있다.
    private final JdbcTemplate jdbcTemplate;
    //얘는 Injection을 받을 수 있는건 아니다.
    //대신 DataSource를 Injection 받는다.
    //생성자가 딱 하나만 있으면 Autowired 생략 가능
    //@Autowired
    //Spring이 자동으로 dataSource를 injection해준다.
    public JdbcTemplateMemberRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        //이렇게 쓰면 된다.
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        //insert문 만들기
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long Id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), Id);
                //나오는 결과를 로우 매퍼라는 것으로 매핑을 해줘야 한다.
        //list로 나온 멤버 객체 list를
        return result.stream().findAny();
        //optional로 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        //나오는 결과를 로우 매퍼라는 것으로 매핑을 해줘야 한다.
        //list로 나온 멤버 객체 list를
        return result.stream().findAny();
        //optional로 반환
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }
    //쿼리문의 결과를 매핑할 RowMapper
    private RowMapper<Member> memberRowMapper(){
        //지금은 이름없는 객체인데 option + enter로 람다로 바꿀 수 있다.
        return (rs, rowNum) -> {
            Member member = new Member();
            //mapRow의 매개변수로 rs가 넘어 온다.
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
        //이렇게 생성한 rowMapper를 조회 쿼리 뒤의 매개변수로 넣어주면 된다.
    }
}
