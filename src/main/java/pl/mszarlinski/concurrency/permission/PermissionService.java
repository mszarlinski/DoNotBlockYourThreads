package pl.mszarlinski.concurrency.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mszarlinski.concurrency.util.Sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final ExecutorService executorService;

    public Permission getSyncPermission(final String userName, final String permName) {
        Sleep.milis(500);
        return permissionRepository.findByUserNameAndName(userName, permName);
    }

    public Future<Permission> getAsyncPermission(final String userName, final String permName) {
        return executorService.submit(() -> getSyncPermission(userName, permName));
    }
}
