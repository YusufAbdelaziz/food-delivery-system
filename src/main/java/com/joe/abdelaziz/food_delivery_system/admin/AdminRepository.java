package com.joe.abdelaziz.food_delivery_system.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joe.abdelaziz.food_delivery_system.role.RoleType;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

  @Query("SELECT a FROM Admin a WHERE a.role.type = :roleType")
  List<Admin> findAllByRoleType(RoleType roleType);
}
