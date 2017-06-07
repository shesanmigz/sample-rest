package com.sample.platform.ui.api.controller;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import com.xebialabs.restito.semantics.Call;
import com.xebialabs.restito.semantics.Stub;
import com.xebialabs.restito.server.StubServer;
import com.xebialabs.restito.support.log.CallsHelper;

/**
 * The default implementation doesn't allow configuring the underlying Grizzly
 * server, so we have to extended by doing almost the same things.
 */
public class CustomStubServer extends StubServer {
	private static final int PORT = 8090;
	
	private final List<Call> calls = new CopyOnWriteArrayList<>();
	private final List<Stub> stubs = new CopyOnWriteArrayList<>();
	private final HttpServer simpleServer;

	public CustomStubServer() {
		this.simpleServer = HttpServer.createSimpleServer(null, CustomStubServer.PORT);
	}

	@Override
	public StubServer addStub(final Stub s) {
		this.stubs.add(s);
		return this;
	}

	@Override
	public StubServer clearStubs() {
		this.stubs.clear();
		return this;
	}

	@Override
	public StubServer clearCalls() {
		this.calls.clear();
		return this;
	}

	@Override
	public StubServer clear() {
		this.clearStubs();
		this.clearCalls();
		return this;
	}

	@Override
	public StubServer run() {
		this.simpleServer.getServerConfiguration().setAllowPayloadForUndefinedHttpMethods(true);
		this.simpleServer.getServerConfiguration().addHttpHandler(this.stubsToHandler(), "/");
		try {
			this.simpleServer.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public void start() {
		this.run();
	}

	@Override
	public StubServer stop() {
		this.simpleServer.shutdownNow();
		return this;
	}

	@Override
	public StubServer secured() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPort() {
		return this.simpleServer.getListeners().iterator().next().getPort();
	}

	@Override
	public List<Call> getCalls() {
		return Collections.unmodifiableList(this.calls);
	}

	@Override
	public List<Stub> getStubs() {
		return Collections.unmodifiableList(this.stubs);
	}

	private HttpHandler stubsToHandler() {
		return new HttpHandler() {
			@Override
			public void service(final Request request, final Response response) throws Exception {
				Call call = Call.fromRequest(request);

				CallsHelper.logCall(call);

				boolean processed = false;
				ListIterator<Stub> iterator = CustomStubServer.this.stubs.listIterator(CustomStubServer.this.stubs.size());
				while (iterator.hasPrevious()) {
					Stub stub = iterator.previous();
					if (!stub.isApplicable(call)) {
						continue;
					}

					stub.apply(response);
					processed = true;
					break;
				}

				if (!processed) {
					response.setStatus(org.glassfish.grizzly.http.util.HttpStatus.NOT_FOUND_404);
				}

				CustomStubServer.this.calls.add(call);
			}
		};
	}
}