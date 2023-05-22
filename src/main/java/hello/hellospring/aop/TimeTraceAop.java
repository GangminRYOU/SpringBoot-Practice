package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//AOP는 @Aspect를 적어줘야 AOP로 쓸 수 있다.
@Aspect
//@Component scan으로 하기도 한다.
//근데 AOP는 정형화해서 쓰기보다는 아 이게 AOP구나 하고 인지할 수 있는게 낫다.
@Component
//여기서는 그냥 Component Scan을 쓰겠다.
public class TimeTraceAop {
    //얘를 Spring Bean으로 등록을 해줘야 한다.

    //@Around를 적용해주어야 한다.
    //Around를 통해 공통 관심사항을 타겟팅을 해줄 수 있다.
    @Around("execution(* hello.hellospring..*(..))")
    //package명.하위패키지.클래스 명 등등 원하는 조건을 넣을 수 있다.
    //패키지 하위에다는 다 적용해라는 의미
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        //이 안에 어떤 메소드를 call하는지 다 얻을 수 있다.
        System.out.println("START " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timems = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timems  + "ms");
        }

    }
}
