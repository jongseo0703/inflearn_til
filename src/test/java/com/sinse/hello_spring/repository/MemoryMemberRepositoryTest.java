package com.sinse.hello_spring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.sinse.hello_spring.domain.Member;

public class MemoryMemberRepositoryTest {
  MemoryMemberRepository repository = new MemoryMemberRepository();

  @AfterEach
  public void afterEach() {
    repository.clearStore();
  }

  @Test
  public void save() {
    Member member = new Member();
    member.setName("Spring");

    repository.save(member);

    Member result = repository.findById(member.getId()).orElse(new Member());
    assertEquals(member, result);
  }

  @Test
  public void findByName() {
    Member member1 = new Member();
    member1.setName("Spring1");
    repository.save(member1);

    Member member2 = new Member();
    member2.setName("Spring2");
    repository.save(member2);

    Member result = repository.findByName("Spring1").orElse(new Member());

    assertThat(member1).isEqualTo(result);
  }

  @Test
  public void findAll() {
    Member member1 = new Member();
    member1.setName("Spring1");
    repository.save(member1);

    Member member2 = new Member();
    member2.setName("Spring2");
    repository.save(member2);

    List<Member> result = repository.findAll();

    assertEquals(2, result.size());
  }
}
