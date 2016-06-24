package pl.mszarlinski.concurrency.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mszarlinski.concurrency.util.Sleep;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionsService {

    private final PermissionsRepository repository;

    public Permissions getPermsSync(final String userName) {
        Sleep.milis(500);
        return repository.findForUserName(userName);
    }

}
