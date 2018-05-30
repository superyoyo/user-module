package com.schoolcampus.handler;

public class Handler {
    private Callback mCallback;
    private Looper mLooper;
    private MessageQueue mQueue;

    public Handler(Looper looper) {
        this.mLooper = looper;
        mQueue = looper.getQueue();
    }

    public Handler(Callback callback, Looper looper) {
        this.mCallback = callback;
        this.mLooper = looper;
        mQueue = looper.getQueue();
    }

    public void dispatchMessage(Message msg) {
        if (mCallback != null) {
            if (mCallback.handleMessage(msg)) {
                return;
            }
        }
        handleMessage(msg);
    }

    public void handleMessage(Message msg) {
    }

    public interface Callback {
        public boolean handleMessage(Message msg);
    }

    public final boolean sendMessage(Message msg) {
        return sendMessageDelayed(msg, 0);
    }

    public final boolean sendMessageDelayed(Message msg, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, System.currentTimeMillis() + delayMillis);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }

    private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        msg.target = this;
        return queue.enqueueMessage(msg, uptimeMillis);
    }

    public final void removeMessages(int what) {
        mQueue.removeMessages(this, what, null);
    }
}
