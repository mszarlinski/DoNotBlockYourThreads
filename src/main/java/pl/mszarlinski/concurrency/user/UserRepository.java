package pl.mszarlinski.concurrency.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mszarlinski on 2016-06-25.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
