package pl.mszarlinski.concurrency.permission;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.util.Sleep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private final PermissionRepository permissionRepository;

    private final ListeningExecutorService executorService;

    public Permission getSyncPermission(final String userName, final String permName) {
        Sleep.milis(500);
        return permissionRepository.findByUserNameAndName(userName, permName);
    }

    public ListenableFuture<Permission> getAsyncPermission(final String userName, final String permName) {
        return executorService.submit(() -> getSyncPermission(userName, permName));
    }
}
