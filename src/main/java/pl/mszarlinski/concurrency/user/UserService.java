package pl.mszarlinski.concurrency.user;

import org.springframework.stereotype.Service;
import pl.mszarlinski.concurrency.util.Sleep;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
public class UserService {

    public User getUserSync() {
        Sleep.milis(500);
        return new User("Maciek", 29);
    }

}
