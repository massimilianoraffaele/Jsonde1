package com.jsonde.profiler;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 
 * @author admin
 *
 */
public class DaemonThreadFactory implements ThreadFactory {

    private AtomicInteger threadIdGenerator = new AtomicInteger();
    private ThreadGroup threadGroup = new ThreadGroup("jSonde-daemon-thread-group");

    /**
     * 
     * @return
     */
    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    public Thread newThread(Runnable r) {

        Thread thread = new Thread(
                threadGroup,
                r,
                "jSonde-daemon-thread-" + threadIdGenerator.getAndIncrement());

        thread.setDaemon(true);

        return thread;
    }

}
