package com.schoolcampus.handler;

import java.util.HashMap;

public class Message {
    public int what;
    HashMap data;
    public long when;
    Handler target;

    static final int FLAG_IN_USE = 1 << 0;
    static final int FLAG_ASYNCHRONOUS = 1 << 1;
    static final int FLAGS_TO_CLEAR_ON_COPY_FROM = FLAG_IN_USE;

    private int flags;
    Message next;

    private static final Object sPoolSync = new Object();
    private static Message sPool;
    private static int sPoolSize = 0;

    private static final int MAX_POOL_SIZE = 50;
    private static boolean gCheckRecycle = true;

    private Message() {
        data = new HashMap();
    }

    public Message put(String key, Object value){
        data.put(key, value);
        return this;
    }

    public String getString(String key, String defValue){
        if(data.containsKey(key)){
            return (String)data.get(key);
        }
        return defValue;
    }

    public int getInt(String key, int defValue){
        if(data.containsKey(key)){
            return (int)data.get(key);
        }
        return defValue;
    }

    public float getFloat(String key, float defValue){
        if(data.containsKey(key)){
            return (float)data.get(key);
        }
        return defValue;
    }

    public long getLong(String key, long defValue){
        if(data.containsKey(key)){
            return (long)data.get(key);
        }
        return defValue;
    }

    public Object get(String key){
        return data.get(key);
    }

    public static Message obtain(){
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }

    void recycle() {
        if (isInUse()) {
            if (gCheckRecycle) {
                throw new IllegalStateException("This message cannot be recycled because it "
                        + "is still in use.");
            }
            return;
        }
        recycleUnchecked();
    }

    boolean isInUse() {
        return ((flags & FLAG_IN_USE) == FLAG_IN_USE);
    }

    void markInUse() {
        flags |= FLAG_IN_USE;
    }

    void recycleUnchecked() {
        // Mark the message as in use while it remains in the recycled object pool.
        // Clear out all other details.
        flags = FLAG_IN_USE;
        what = 0;
        data.clear();
        when = 0;
        target = null;

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }
}
