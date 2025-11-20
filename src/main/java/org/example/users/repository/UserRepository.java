package org.example.users.repository;

import org.example.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(PathVariable userId);

    void deleteById(PathVariable userId);
}
