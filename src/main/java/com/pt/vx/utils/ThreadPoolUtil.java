package com.pt.vx.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(3,5,1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getPool(){
        return pool;
    }


}
