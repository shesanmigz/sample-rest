package com.sample.platform.ui.api.web.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.MDC;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import com.sample.platform.ui.api.filters.TraceFilter;

/**
 * Wraps a listenable future and delegates all calls to it.
 * When a response succeeds or fails also sets up the logger MDC
 * by putting a trace id.  
 */
public class MDCAwareListenableFutureDecorator<T> implements ListenableFuture<T> {
	private final ListenableFuture<T> delegate;
	private final String traceId;

	public MDCAwareListenableFutureDecorator(final String traceId, final ListenableFuture<T> delegate) {
		this.traceId = traceId;
		this.delegate = delegate;
	}

	@Override
	public boolean cancel(final boolean mayInterruptIfRunning) {
		return this.delegate.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return this.delegate.isCancelled();
	}

	@Override
	public boolean isDone() {
		return this.delegate.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		return this.delegate.get();
	}

	@Override
	public T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.delegate.get(timeout, unit);
	}

	@Override
	public void addCallback(final ListenableFutureCallback<? super T> callback) {
		this.delegate.addCallback(new ListenableFutureCallback<T>() {
			@Override
			public void onSuccess(final T result) {
				MDCAwareListenableFutureDecorator.this.onSuccess(result, callback::onSuccess);
			}

			@Override
			public void onFailure(final Throwable ex) {
				MDCAwareListenableFutureDecorator.this.onFailure(ex, callback::onFailure);
			}
		});
	}

	@Override
	public void addCallback(final SuccessCallback<? super T> successCallback, final FailureCallback failureCallback) {
		this.delegate.addCallback(new SuccessCallback<T>() {
			@Override
			public void onSuccess(final T result) {
				MDCAwareListenableFutureDecorator.this.onSuccess(result, successCallback::onSuccess);
			}
		}, new FailureCallback() {
			@Override
			public void onFailure(final Throwable ex) {
				MDCAwareListenableFutureDecorator.this.onFailure(ex, failureCallback::onFailure);
			}
		});
	}

	private <C> void onSuccess(final C result, final Delegate<C> delegate) {
		MDC.put(TraceFilter.MDC_KEY, MDCAwareListenableFutureDecorator.this.traceId);
		try {
			delegate.delegate(result);
		} finally {
			MDC.remove(TraceFilter.MDC_KEY);
		}
	}

	private void onFailure(final Throwable ex, final Delegate<Throwable> delegate) {
		MDC.put(TraceFilter.MDC_KEY, MDCAwareListenableFutureDecorator.this.traceId);
		try {
			delegate.delegate(ex);
		} finally {
			MDC.remove(TraceFilter.MDC_KEY);
		}
	}
	
	interface Delegate<C> {
		void delegate(C result);
	}
}