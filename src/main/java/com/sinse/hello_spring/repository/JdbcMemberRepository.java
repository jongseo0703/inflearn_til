package com.sinse.hello_spring.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.sinse.hello_spring.domain.Member;

public class JdbcMemberRepository implements MemberRepository {
  private final DataSource dataSource;

  public JdbcMemberRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Member save(Member member) {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "insert into member(name) values(?)";

    try {
      conn = getConnection();

      pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      pstmt.setString(1, member.getName());

      pstmt.executeUpdate();

      rs = pstmt.getGeneratedKeys();

      if (rs.next()) {
        member.setId(rs.getLong(1));
      } else {
        throw new SQLException("ID 조회 실패");
      }
      return member;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  @Override
  public Optional<Member> findById(Long id) {
    String sql = "select * from member where id = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();

      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, id);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        Member member = new Member();

        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));

        return Optional.of(member);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  @Override
  public Optional<Member> findByName(String name) {
    return Optional.empty();
  }

  @Override
  public List<Member> findAll() {
    String sql = "select * from member";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = getConnection();

      pstmt = conn.prepareStatement(sql);

      rs = pstmt.executeQuery();

      List<Member> memberList = new ArrayList<>();

      while (rs.next()) {
        Member member = new Member();

        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));

        memberList.add(member);
      }
      return memberList;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  private Connection getConnection() {
    return DataSourceUtils.getConnection(dataSource);
  }

  private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      if (pstmt != null) {
        pstmt.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
