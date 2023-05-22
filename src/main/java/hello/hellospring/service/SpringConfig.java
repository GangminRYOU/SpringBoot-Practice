package hello.hellospring.service;

import hello.hellospring.Domain.Member;
import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    //dataSource대신 EntityManager추가
    //private EntityManager em;
    //원래 스펙에서는
    //@PersistenceContext 로 받아야한다.

    private final MemberRepository memberRepository;
    public SpringConfig(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    //@Autowired
    //public SpringConfig(EntityManager em){
    //   this.em = em;
    //}
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }
    //이렇게 하면 "아 Timetrace하는데 AOP가 사용되고 있구나"
   /* @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }*/
/*    @Bean
    public MemberRepository memberRepository(){
        ///return new MemoryMemberRepository();
        //return new JDBCMemberRespository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
        //얘도 EntityManager가 필요하다.
    }*/
    //interface는 new가 안된다. -> 구현체를 생성해서 반환해줘야 한다.
}
