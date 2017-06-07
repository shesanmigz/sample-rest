package com.sample.platform.ui.api.controller;

import java.lang.reflect.Array;
import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;

import org.assertj.core.util.Sets;
import org.glassfish.grizzly.http.Method;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.builder.verify.VerifyHttp;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;

/**
 * Base class for tests sending HTTP requests and expecting HTTP responses.
 * Please notice, two servers may be involved in testing: a test server which
 * runs the code being tested; and a mock server that the test code may use to make
 * its own HTTP requests.
 */
public abstract class WithMocks {
	protected final ObjectMapper om;

	protected MockMvc mockMvc = null;
	protected StubServer mockServer = null;

	public WithMocks() {
		this.om = new ObjectMapper();
		this.om.registerModule(new JodaModule());
		this.om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	protected MockMvc createMockMVC(final WebApplicationContext wac, final Filter... filters) {
		return MockMvcBuilders.webAppContextSetup(wac).addFilters(filters).build();
	}

	protected void startMockServer() {
		if (this.mockServer != null) {
			throw new IllegalStateException();
		}
		this.mockServer = new CustomStubServer();
		this.mockServer.run();
	}

	protected void stopMockServer() {
		if (this.mockServer != null) {
			this.mockServer.stop();
			this.mockServer = null;
		}
	}

	public StubBuilder stub() {
		return this.new StubBuilder();
	}

	public TestBuilder test() {
		return this.new TestBuilder(true);
	}

	public TestBuilder syncTest() {
		return this.new TestBuilder(false);
	}

	public static class ServerCall {
		public final ImmutableMap<String, String> params;
		public final int status;
		public final Method method;
		public final String path;

		public ServerCall(final Map<String, String> params, final int status, final Method method, final String path) {
			this.params = params == null ? ImmutableMap.of() : ImmutableMap.copyOf(params);
			this.status = status;
			this.method = method;
			this.path = path;
		}
	}

	/**
	 * Sets up stub data of a mock server which it should send back when it's
	 * called.
	 */
	public class StubBuilder {
		private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		private final Map<String, String> params = new HashMap<>();

		private int status = HttpStatus.OK.value();

		private Method method;
		private String path;
		private String body;

		public StubBuilder withStatus(final int status) {
			this.status = status;
			return this;
		}

		public StubBuilder withStatus(final HttpStatus status) {
			return this.withStatus(status.value());
		}

		public StubBuilder withParam(final String name, final String value) {
			this.params.put(name, value);
			return this;
		}

		/**
		 * Makes the mock server to wait for a GET request with the given base URL
		 */
		public StubBuilder withGET(final String path) {
			if (this.method != null) {
				throw new IllegalSelectorException();
			}
			this.method = Method.GET;
			this.path = path;
			return this;
		}

		/**
		 * Makes the mock server to wait for a POST request with the given base URL
		 */
		public StubBuilder withPOST(final String path) {
			if (this.method != null) {
				throw new IllegalSelectorException();
			}
			this.method = Method.POST;
			this.path = path;
			return this;
		}

		/**
		 * Makes the mock server to wait for a PUT request with the given base URL
		 */
		public StubBuilder withPUT(final String path) {
			if (this.method != null) {
				throw new IllegalSelectorException();
			}
			this.method = Method.PUT;
			this.path = path;
			return this;
		}

		/**
		 * Makes the mock server to wait for a DELETE request with the given base
		 * URL
		 */
		public StubBuilder withDELETE(final String path) {
			if (this.method != null) {
				throw new IllegalSelectorException();
			}
			this.method = Method.DELETE;
			this.path = path;
			return this;
		}

		/**
		 * Makes the mock server to send a JSON object when a request comes to the
		 * specified URL with the specified method. Content-Type header will be set
		 * to application/json
		 */
		public StubBuilder withPOJO(final Object body) throws JsonProcessingException {
			this.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
			return this.withBody(WithMocks.this.om.writeValueAsString(body));
		}

		/**
		 * Makes the mock server to send a text when a request comes to the
		 * specified URL with the specified method.
		 */
		public StubBuilder withBody(final String body) throws JsonProcessingException {
			this.body = body;
			return this;
		}

		/**
		 * Makes the mock server to send a header when a request comes to the
		 * specified URL with the specified method.
		 */
		public StubBuilder withHeader(final String name, final String value) {
			this.headers.add(name, value);
			return this;
		}

		/**
		 * Sets up the mock server.
		 */
		public ServerCall build() {
			ServerCall serverCall = new ServerCall(this.params, this.status, this.method, this.path);
			
			if (WithMocks.this.mockServer != null) {
				List<Action> actions = new ArrayList<>();
				actions.add(Action.status(org.glassfish.grizzly.http.util.HttpStatus.getHttpStatus(this.status)));

				for (final Entry<String, List<String>> e : this.headers.entrySet()) {
					if (!e.getValue().isEmpty()) {
						if (e.getValue().size() > 1) {
							throw new IllegalStateException();
						}
						actions.add(Action.header(e.getKey(), e.getValue().iterator().next()));
					}
				}
				if (Strings.nullToEmpty(this.body).isEmpty()) {
					actions.add(Action.noContent());
				} else {
					actions.add(Action.stringContent(this.body));
				}

				List<Condition> conditions = new ArrayList<>();
				if (this.method == Method.GET) {
					conditions.add(Condition.get(this.path));
				} else if (this.method == Method.POST) {
					conditions.add(Condition.post(this.path));
				} else if (this.method == Method.DELETE) {
					conditions.add(Condition.delete(this.path));
				} else if (this.method == Method.PUT) {
					conditions.add(Condition.put(this.path));
				}
				for (final Entry<String, String> e : this.params.entrySet()) {
					conditions.add(Condition.parameter(e.getKey(), e.getValue()));
				}

				StubHttp.whenHttp(WithMocks.this.mockServer).match(conditions.toArray(new Condition[conditions.size()])).then(actions.toArray(new Action[actions.size()]));
			}

			return serverCall;
		}
	}

	/**
	 * Sets up data to send and data to expect.
	 */
	public class TestBuilder {
		private final MultiValueMap<String, String> headersToSend = new LinkedMultiValueMap<>();
		private final MultiValueMap<String, String> headersToExpect = new LinkedMultiValueMap<>();
		private final MultiValueMap<String, String> serverHeadersToExpect = new LinkedMultiValueMap<>();

		private final boolean async;

		private int status = HttpStatus.OK.value();

		private MockHttpServletRequestBuilder request;
		private BodyComparator bodyComparator;
		private String body;
		private ServerCall serverCall;

		public TestBuilder(final boolean async) {
			this.async = async;
		}

		/**
		 * Prepares to send a GET request with the given path.
		 */
		public TestBuilder withGET(final String path) {
			if (this.request != null) {
				throw new IllegalStateException();
			}
			this.request = MockMvcRequestBuilders.get(path);
			return this;
		}

		/**
		 * Prepares to send a POST request with the given path.
		 */
		public TestBuilder withPOST(final String path) {
			if (this.request != null) {
				throw new IllegalStateException();
			}
			this.request = MockMvcRequestBuilders.post(path);
			return this;
		}

		/**
		 * Prepares to send a PUT request with the given path.
		 */
		public TestBuilder withPUT(final String path) {
			if (this.request != null) {
				throw new IllegalStateException();
			}
			this.request = MockMvcRequestBuilders.put(path);
			return this;
		}

		/**
		 * Prepares to send a DELETE request with the given path.
		 */
		public TestBuilder withDELETE(final String path) {
			if (this.request != null) {
				throw new IllegalStateException();
			}
			this.request = MockMvcRequestBuilders.delete(path);
			return this;
		}

		/**
		 * Prepares to send a JSON object with the Content-Type header set to
		 * application/json.
		 */
		public TestBuilder withPOJO(final Object body) throws JsonProcessingException {
			this.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
			return this.withBody(WithMocks.this.om.writeValueAsString(body));
		}

		/**
		 * Prepares to send a text.
		 */
		public TestBuilder withBody(final String body) throws JsonProcessingException {
			this.body = body;
			return this;
		}

		/**
		 * Prepares to send a header.
		 */
		public TestBuilder withHeader(final String name, final String value) {
			this.checkHeaders(value, this.headersToSend.get(name));
			this.headersToSend.add(name, value);
			return this;
		}

		/**
		 * Checks if the mock server has received a header. #expectServerCall must be
		 * called before.
		 */
		public TestBuilder expectServerHeader(final String name, final String value) {
			this.checkHeaders(value, this.serverHeadersToExpect.get(name));
			this.serverHeadersToExpect.add(name, value);
			return this;
		}

		/**
		 * Checks if the mock server has not received a header. #expectServerCall
		 * must be called before.
		 */
		public TestBuilder notExpectServerHeader(final String name) {
			return this.expectServerHeader(name, null);
		}
		
		/**
		 * Checks if the call was made to the mock server.
		 */
		public TestBuilder expectServerCall(final ServerCall serverCall) {
			this.serverCall = serverCall;
			return this;
		}

		/**
		 * Checks if the test server has responded with a status.
		 */
		public TestBuilder expectStatus(final int status) {
			this.status = status;
			return this;
		}

		/**
		 * Checks if the test server has responded with a status.
		 */
		public TestBuilder expectStatus(final HttpStatus status) {
			return this.expectStatus(status.value());
		}

		/**
		 * Checks if the test server has responded with a header.
		 */
		public TestBuilder expectHeader(final String name, final String value) {
			this.checkHeaders(value, this.headersToExpect.get(name));
			this.headersToExpect.add(name, value);
			return this;
		}

		/**
		 * Checks if the test server has not sent a header.
		 */
		public TestBuilder notExpectHeader(final String name) {
			return this.expectHeader(name, null);
		}

		private void checkHeaders(final String value, final List<String> values) {
			if (values != null && !values.isEmpty()) {
				if (values.iterator().next() == null) {
					if (value != null) {
						throw new IllegalArgumentException();
					}
				} else {
					if (value == null) {
						throw new IllegalArgumentException();
					}
				}
			}
		}

		/**
		 * Checks if the test server has responded with a JSON object, it also
		 * checks if the test server has set Content-Type header to application/json
		 */
		public <T> TestBuilder expectPOJO(final T body) {
			this.expectHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
			this.bodyComparator = new JSONBody<>(body);
			return this;
		}

		/**
		 * Checks if the test server has sent a collection. The result collection
		 * and the expected collection should contain the same amount of elements;
		 * the same elements but the order is not important.
		 */
		public <T> TestBuilder expectCollection(final Collection<? extends T> collection, final Class<T> type) {
			this.expectHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
			this.bodyComparator = new CollectionBody<>(collection, type);
			return this;
		}

		/**
		 * Checks if the test server has sent an empty collection/empty array.
		 */
		public <T> TestBuilder expectEmptyCollection(final Class<T> type) {
			return this.expectCollection(Collections.emptyList(), type);
		}

		/**
		 * Checks if the test server has sent a collection. The result collection
		 * and the expected collection should contain the same amount of elements
		 * and the same elements in the same order.
		 */
		public <T> TestBuilder expectList(final Collection<? extends T> collection, final Class<T> type) {
			this.expectHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
			this.bodyComparator = new ListBody<>(collection, type);
			return this;
		}

		/**
		 * Checks if the test server has sent a text.
		 */
		public TestBuilder expectBody(final String body) {
			this.bodyComparator = new TextBody(body);
			return this;
		}

		/**
		 * Checks if the test server has sent an empty body.
		 */
		public TestBuilder expectEmptyBody() {
			return this.expectBody("");
		}

		/**
		 * Runs the set up test.
		 *
		 * The test will be executed even if a mock server is not specified.
		 */
		public void run() throws Exception {
			this.run(false);
		}

		/**
		 * Runs the set up test.
		 *
		 * If no mock server is given the test will be skipped.
		 */
		public void runWithServer() throws Exception {
			this.run(true);
		}

		/**
		 * Runs the set up test.
		 *
		 * @param runWithServer
		 *          if true but no mock server is given the test will be skipped.
		 */
		public void run(final boolean runWithServer) throws Exception {
			if (runWithServer && WithMocks.this.mockServer == null) {
				return;
			}

			if (!this.serverHeadersToExpect.isEmpty() && this.serverCall == null) {
				throw new IllegalStateException();
			}

			if (WithMocks.this.mockServer != null) {
				WithMocks.this.mockServer.clearCalls();
			}

			for (final Entry<String, List<String>> entry : this.headersToSend.entrySet()) {
				Object[] values = entry.getValue().toArray(new Object[entry.getValue().size()]);
				this.request = this.request.header(entry.getKey(), values);
			}

			if (this.body != null) {
				this.request = this.request.content(this.body);
			}

			ResultActions actions = WithMocks.this.mockMvc.perform(this.request);
			if (this.async) {
				actions = actions.andExpect(MockMvcResultMatchers.request().asyncStarted());
				actions = WithMocks.this.mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(actions.andReturn()));
			}

			actions = actions.andExpect(MockMvcResultMatchers.status().is(this.status));

			for (final Entry<String, List<String>> entry : this.headersToExpect.entrySet()) {
				if (entry.getValue() != null && !entry.getValue().isEmpty() && entry.getValue().iterator().next() == null) {
					actions = actions.andExpect(MockMvcResultMatchers.header().string(entry.getKey(), CoreMatchers.nullValue()));
				} else {
					String[] values = entry.getValue().toArray(new String[entry.getValue().size()]);

					actions = actions.andExpect(MockMvcResultMatchers.header().stringValues(entry.getKey(), values));
				}
			}

			if (this.bodyComparator != null) {
				this.bodyComparator.compare(actions.andReturn());
			}

			if (this.serverCall != null && WithMocks.this.mockServer != null) {
				List<Condition> conditions = new ArrayList<>();
				conditions.add(Condition.uri(this.serverCall.path));

				for (final Entry<String, List<String>> entry : this.serverHeadersToExpect.entrySet()) {
					if (entry.getValue() != null && !entry.getValue().isEmpty() && entry.getValue().iterator().next() == null) {
						conditions.add(Condition.not(Condition.withHeader(entry.getKey())));
					} else {
						conditions.add(Condition.withHeader(entry.getKey(), entry.getValue().iterator().next()));
					}
				}

				for (final Entry<String, String> entry: this.serverCall.params.entrySet()) {
					conditions.add(Condition.parameter(entry.getKey(), entry.getValue()));
				}

				VerifyHttp.verifyHttp(WithMocks.this.mockServer)
					.once(conditions.toArray(new Condition[conditions.size()]));
			}
		}
	}

	private interface BodyComparator {
		void compare(MvcResult result) throws Exception;
	}

	private class TextBody implements BodyComparator {
		private final String text;

		public TextBody(final String text) {
			this.text = text;
		}

		@Override
		public void compare(final MvcResult result) throws Exception {
			Assert.assertEquals(this.text, result.getResponse().getContentAsString());
		}
	}

	private class JSONBody<T> implements BodyComparator {
		private final T bodyToExpect;

		public JSONBody(final T bodyToExpect) {
			this.bodyToExpect = bodyToExpect;
		}

		@Override
		public void compare(final MvcResult result) throws Exception {
			Assert.assertEquals(this.bodyToExpect,
					WithMocks.this.om.readValue(result.getResponse().getContentAsString(), this.bodyToExpect.getClass()));
		}
	}

	private abstract class IterableBody<T> implements BodyComparator {
		private final Class<?> arrayType;

		public IterableBody(final Class<T> type) {
			this.arrayType = Array.newInstance(type, 0).getClass();
		}

		protected List<T> getList(final MvcResult result) throws Exception {
			String body = result.getResponse().getContentAsString();
			T[] array = (T[]) WithMocks.this.om.readValue(body, this.arrayType);
			List<T> list = Arrays.asList(array);
			return list;
		}
	}

	private class CollectionBody<T> extends IterableBody<T> {
		protected final ImmutableSet<? extends T> bodyToExpect;

		public CollectionBody(final Collection<? extends T> bodyToExpect, final Class<T> type) {
			super(type);

			this.bodyToExpect = ImmutableSet.copyOf(bodyToExpect);
		}

		@Override
		public void compare(final MvcResult result) throws Exception {
			Assert.assertEquals(this.bodyToExpect, Sets.newHashSet(this.getList(result)));
		}
	}

	private class ListBody<T> extends IterableBody<T> {
		private ImmutableList<? extends T> bodyToExpect;

		public ListBody(final Collection<? extends T> bodyToExpect, final Class<T> type) {
			super(type);

			this.bodyToExpect = ImmutableList.copyOf(bodyToExpect);
		}

		@Override
		public void compare(final MvcResult result) throws Exception {
			Assert.assertEquals(this.bodyToExpect, this.getList(result));
		}
	}
}
