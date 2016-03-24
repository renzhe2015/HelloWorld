package com.cifpay.cifpaylib.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * 
 * @author admin
 *
 */
public final class CifPayExector {
	private static final int CORE_POOL_SIZE = 5;
	private static final int MAX_POOL_SIZE = 10;
	private static final int KEEP_ALIVE_TIME = 5000;
	private static BlockingQueue<Runnable> queue = null;
	private static Executor executor = null;

	public static void init() {
		if (null == executor) {
			queue = new LinkedBlockingQueue<Runnable>();
			executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
					KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, queue,
					new ThreadPoolExecutor.DiscardOldestPolicy());
		}
	}

	public static Executor getInstance() {
		init();
		return executor;
	}
}
