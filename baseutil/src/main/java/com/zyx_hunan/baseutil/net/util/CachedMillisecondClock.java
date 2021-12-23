package com.zyx_hunan.baseutil.net.util;

/**
 * @author gongzw
 * @date 2021-09-13 16:06
 */
public enum CachedMillisecondClock {
    INS;
    private volatile long now = 0;// 当前时间

    private CachedMillisecondClock() {
        this.now = System.currentTimeMillis();
        start();
    }

    private void start() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    now = System.currentTimeMillis();
                }
            }
        }, "CachedMillisecondClockUpdater");
        t.setDaemon(true);
        t.start();
    }

    public long now() {
        return now;
    }

}
