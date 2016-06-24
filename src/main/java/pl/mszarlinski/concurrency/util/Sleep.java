package pl.mszarlinski.concurrency.util;

/**
 * @author mszarlinski on 2016-06-24.
 */
public class Sleep {

    public static void milis(final long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
