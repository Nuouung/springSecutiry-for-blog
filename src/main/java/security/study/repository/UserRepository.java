package security.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.study.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
