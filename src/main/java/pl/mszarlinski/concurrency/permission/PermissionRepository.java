package pl.mszarlinski.concurrency.permission;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mszarlinski on 2016-06-24.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByUserNameAndName(String userName, String name);
}
