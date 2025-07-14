package com.sinse.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceApp {

  @Around("execution(* com.sinse.hello_spring..*(..))")
  public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
    Long start = System.currentTimeMillis();
    System.out.println("START: " + joinPoint.toString());
    try {
      return joinPoint.proceed();
    } finally {
      Long end = System.currentTimeMillis();
      Long timeMs = end - start;
      System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
    }
  }
}
