package com.test.transformerbattle.domain.scheduler;

import io.reactivex.Scheduler;

/**
 * Interface used to encapsulate UI thread and any framework thread.
 * */
public interface DefaultScheduler {
    Scheduler getScheduler();
}
