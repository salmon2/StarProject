package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
