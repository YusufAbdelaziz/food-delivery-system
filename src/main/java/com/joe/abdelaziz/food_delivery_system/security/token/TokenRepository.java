package com.joe.abdelaziz.food_delivery_system.security.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query("""
      select t from Token t inner join t.user u
      where u.id = :id and (t.expired = false and t.revoked = false)
      """)
  List<Token> findAllValidTokenByUser(Long id);

  @Query("""
      select t from Token t inner join t.courier c
      where c.id = :id and (t.expired = false and t.revoked = false)
      """)
  List<Token> findAllValidTokenByCourier(Long id);

  Optional<Token> findByToken(String token);
}