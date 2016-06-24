package pl.mszarlinski.concurrency;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mszarlinski.concurrency.broadcast.Channel;
import pl.mszarlinski.concurrency.broadcast.ChannelService;
import pl.mszarlinski.concurrency.permission.Permissions;
import pl.mszarlinski.concurrency.permission.PermissionsService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

/**
 * @author mszarlinski on 2016-06-24.
 */
@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SyncEndpoints {

    private final UserService userService;

    private final PermissionsService permissionsService;

    private final ChannelService channelService;

    @RequestMapping("/user")
    public User getUser() {
        return userService.getUserSync();
    }

    @RequestMapping("/perms/{userName}")
    public Permissions getPermissions(final @PathVariable String userName) {
        return permissionsService.getPermsSync(userName);
    }

    @RequestMapping("/channel/{channelName}")
    public Channel getChannel(final @PathVariable String channelName) {
        return channelService.getChannelSync(channelName);
    }
}
