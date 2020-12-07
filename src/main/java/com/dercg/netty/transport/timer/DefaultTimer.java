package com.dercg.netty.transport.timer;

import java.util.Timer;
import java.util.TimerTask;

public class DefaultTimer {
    private Timer timer;
    private final int interval = 10000;

    public DefaultTimer(TimerCallBack callBack) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                callBack.callback();
            }
        }, 0, this.interval);
    }

    public DefaultTimer(TimerCallBack callBack, int interval) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                callBack.callback();
            }
        }, 0, interval);
    }

    public void close() {
        timer.cancel();
    }
}
