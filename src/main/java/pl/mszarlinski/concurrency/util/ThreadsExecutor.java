package pl.mszarlinski.concurrency.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mszarlinski on 2016-06-24.
 */
public class ThreadsExecutor {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static ExecutorService get() {
        return executorService;
    }
}
