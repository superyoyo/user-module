package com.schoolcampus.handler;

public class Looper {
    private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
    private MessageQueue mQueue;
    private Thread mThread;

    public MessageQueue getQueue() {
        return mQueue;
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    private Looper() {
        mQueue = new MessageQueue();
        mThread = Thread.currentThread();
    }

    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;

        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                return;
            }

            msg.target.dispatchMessage(msg);


            msg.recycleUnchecked();
        }
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public void quit() {
        mQueue.quit(false);
    }
}
