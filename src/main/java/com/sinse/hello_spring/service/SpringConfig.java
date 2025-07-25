package com.sinse.hello_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sinse.hello_spring.repository.MemberRepository;

@Configuration
public class SpringConfig {

  private MemberRepository memberRepository;

  @Autowired
  public SpringConfig(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository);
  }

  //  @Bean
  //  public MemberRepository memberRepository() {
  // return new MemoryMemberRepository();
  // return new JdbcMemberRepository(dataSource);
  // return new JdbcTemplateRepository(dataSource);
  // return new JpaMemberRepository(em);
  //  }
}
