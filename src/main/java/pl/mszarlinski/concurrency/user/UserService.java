package pl.mszarlinski.concurrency.user;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.util.Sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    private final ExecutorService executorService;

    public User getSyncUser(final String userName) {
        Sleep.milis(500);
        return userRepository.findByName(userName);
    }

    public Future<User> getAsyncUser(final String userName) {
        return executorService.submit(() -> getSyncUser(userName));
    }
}
