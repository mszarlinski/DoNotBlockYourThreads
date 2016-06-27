package pl.mszarlinski.concurrency.user;

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
public class UserService {

    private final UserRepository userRepository;

    private final ListeningExecutorService executorService;

    public User getSyncUser(final String userName) {
        Sleep.milis(500);
        return userRepository.findByName(userName);
    }

    public ListenableFuture<User> getAsyncUser(final String userName) {
        return executorService.submit(() -> getSyncUser(userName));
    }
}
