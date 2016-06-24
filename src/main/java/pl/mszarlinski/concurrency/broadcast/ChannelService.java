package pl.mszarlinski.concurrency.broadcast;

import org.springframework.stereotype.Service;
import pl.mszarlinski.concurrency.util.Sleep;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
public class ChannelService {

    public static final double EXCELLENT = 5.0;

    public Channel getChannelSync(final String channelName) {
        Sleep.milis(500);
        return new Channel(channelName, EXCELLENT);
    }

}
