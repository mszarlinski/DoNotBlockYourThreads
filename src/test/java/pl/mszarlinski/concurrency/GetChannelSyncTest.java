package pl.mszarlinski.concurrency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mszarlinski.concurrency.broadcast.Channel;
import pl.mszarlinski.concurrency.permission.Permissions;
import pl.mszarlinski.concurrency.user.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mszarlinski on 2016-06-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DoNotBlockYourThreadsApplication.class)
public class GetChannelSyncTest {

    @Autowired
    private SyncEndpoints syncEndpoints;

    @Test
    public void shouldReturnSportChannel() {
        // when
        final User user = syncEndpoints.getUser();
        final Permissions permissions = syncEndpoints.getPermissions(user.getName());
        final Channel channel = syncEndpoints.getChannel("SPORT");

        // then
        assertThat(user).isNotNull();
        assertThat(permissions).isNotNull();
        assertThat(channel).isNotNull();
    }
}
