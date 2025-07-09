package com.sinse.hello_spring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sinse.hello_spring.domain.Member;
import com.sinse.hello_spring.repository.MemoryMemberRepository;

class MemberServiceTest {

  MemberService memberService;
  MemoryMemberRepository memoryMemberRepository;

  @BeforeEach
  public void beforeEach() {
    memoryMemberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memoryMemberRepository);
  }

  @AfterEach
  public void afterEach() {
    memoryMemberRepository.clearStore();
  }

  @Test
  void join() {
    // given
    Member member = new Member();
    member.setName("Spring1");

    // when
    Long saveId = memberService.join(member);

    // then
    Member findMember = memberService.findOne(saveId).get();
    assertThat(member.getName()).isEqualTo(findMember.getName());
  }

  @Test
  public void 중복_회원_예외() {
    // given
    Member member1 = new Member();
    member1.setName("Spring1");

    Member member2 = new Member();
    member2.setName("Spring1");

    // when
    memberService.join(member1);
    IllegalStateException e =
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    // then
  }

  @Test
  void findMembers() {}

  @Test
  void findOne() {}
}
