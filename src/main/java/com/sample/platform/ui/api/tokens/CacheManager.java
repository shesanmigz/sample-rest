package com.sample.platform.ui.api.tokens;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * Base class for cache managers that update caches on schedule.
 */
public abstract class CacheManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenCleaningCacheManager.class);
	
	private final String name;
	private final long shutdownTimeout;
	private final ScheduledExecutorService scheduledExecutor;
	
	public CacheManager(final String name, final long period, final long shutdownTimeout) {
		this.name = name;
		this.shutdownTimeout = shutdownTimeout;
		
		CustomizableThreadFactory tf = new CustomizableThreadFactory();
		tf.setDaemon(true);
		tf.setThreadNamePrefix(name + "-");
		
		this.scheduledExecutor = Executors.newSingleThreadScheduledExecutor(tf);
		if (period > 0) {
			this.scheduledExecutor.scheduleAtFixedRate(this::task, period, period, TimeUnit.MILLISECONDS);
		}
		
		LOGGER.debug("Cache manager started for {}", this.name);
	}
	
	public void destroy() throws Exception {
		this.scheduledExecutor.shutdownNow();
		this.scheduledExecutor.awaitTermination(this.shutdownTimeout, TimeUnit.MILLISECONDS);
		if (this.scheduledExecutor.isShutdown()) {
			CacheManager.LOGGER.debug("Cache manager of {} has stopped", this.name);
		} else {
			CacheManager.LOGGER.error("It took too long to shutdown {} scheduler. The shutdown will be forced by the JVM.",
					this.name);
		}
	}
	
	/**
	 * Executes the cache update. The method is triggered by the task scheduler
	 * but may be called explicitly.
	 */
	public abstract void task();
}
