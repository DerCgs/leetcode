package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.timer.DefaultTimer;

import java.util.ArrayList;
import java.util.List;

public class TimerMgr {
    private List<DefaultTimer> timers;

    public TimerMgr() {
        timers = new ArrayList<>();
    }

    public void add(DefaultTimer timer) {
        timers.add(timer);
    }

    public void close() {
        for (DefaultTimer timer : timers) {
            timer.close();
        }
    }
}
