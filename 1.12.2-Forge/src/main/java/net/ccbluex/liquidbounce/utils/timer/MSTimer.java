package net.ccbluex.liquidbounce.utils.timer;

public final class MSTimer {

    private long time = -1L;

    public boolean hasTimePassed(final long MS) {
        return System.currentTimeMillis() >= time + MS;
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    public boolean hasReached(double milliseconds) {
        return (double)(this.getCurrentMS() - this.lastMS) >= milliseconds;
    }
    private long lastMS;
    public long hasTimeLeft(final long MS) {
        return (MS + time) - System.currentTimeMillis();
    }
    public void resetTwo() {
        this.lastMS = this.getCurrentMS();
    }

    public void reset() {
        time = System.currentTimeMillis();
    }
}
