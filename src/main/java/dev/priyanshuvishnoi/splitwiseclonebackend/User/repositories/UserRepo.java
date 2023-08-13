package dev.priyanshuvishnoi.splitwiseclonebackend.User.repositories;

import dev.priyanshuvishnoi.splitwiseclonebackend.User.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    @Query(value = """
                SELECT EXISTS (
                    SELECT 1
                    FROM users
                    WHERE email = :email
                )
        """, nativeQuery = true)
    boolean existsByEmail(String email);
}
