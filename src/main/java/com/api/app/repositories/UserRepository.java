package com.api.app.repositories;

import com.api.app.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
}
