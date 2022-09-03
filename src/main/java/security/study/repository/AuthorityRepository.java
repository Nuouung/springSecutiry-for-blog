package security.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.study.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
