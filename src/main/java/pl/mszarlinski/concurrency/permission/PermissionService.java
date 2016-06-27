package pl.mszarlinski.concurrency.permission;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.util.Sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public CompletableFuture<Permission> getAsyncPermission(final String userName, final String permName) {
        return CompletableFuture.supplyAsync(() -> getSyncPermission(userName, permName), executorService);
    }
}
