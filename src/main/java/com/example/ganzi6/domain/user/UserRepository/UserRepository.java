package com.example.ganzi6.domain.user.UserRepository;

import com.example.ganzi6.domain.user.Role.Role;
import com.example.ganzi6.domain.user.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdAndRole(String loginId, Role role);

    boolean existsByLoginId(String loginId);
}