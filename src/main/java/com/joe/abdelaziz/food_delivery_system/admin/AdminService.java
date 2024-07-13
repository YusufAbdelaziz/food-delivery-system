package com.joe.abdelaziz.food_delivery_system.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.role.Role;
import com.joe.abdelaziz.food_delivery_system.role.RoleService;
import com.joe.abdelaziz.food_delivery_system.role.RoleType;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminService {

  private final AdminRepository adminRepository;

  private final RoleService roleService;

  @Transactional
  public Admin insertAdmin(Admin user) {
    Role role = roleService.findRoleByType(RoleType.ADMIN);
    user.setRole(role);

    Admin insertedUser = adminRepository.save(user);
    return insertedUser;
  }

  public Admin findAdminById(Long id) {
    Admin user = adminRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Admin of record id %d is not found", id)));
    return user;
  }

  public List<Admin> findAll() {
    return adminRepository.findAllByRoleType(RoleType.ADMIN);
  }
}
