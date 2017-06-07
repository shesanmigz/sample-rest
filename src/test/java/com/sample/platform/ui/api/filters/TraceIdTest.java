package com.sample.platform.ui.api.filters;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sample.platform.ui.api.Application;
import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.controller.ShopApiControllerTests;
import com.sample.platform.ui.api.filters.TraceFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@ContextConfiguration
@ActiveProfiles("test")
public class TraceIdTest extends ShopApiControllerTests {
	private String traceId;
	
	@Before
	@Override
	public void setUp() throws Exception {
		this.traceId = UUID.randomUUID().toString();

		this.mockMvc = this.createMockMVC(this.wac, new TraceIdFilter(), new TraceFilter(this.traceId));

		this.startMockServer();
	}
	
	@Test
	public void testWithTraceIdSet() throws Exception {
		ServerCall sc = this.stub()
				.withGET("/trace-id-test-callback")
				.build();
		
		this.test()
				.withGET("/trace-id-test")
				.withHeader(ShopApiConstants.TRACE_HEADER, ShopApiControllerTests.TRACE_HEADER_VALUE)
				.expectHeader(ShopApiConstants.TRACE_HEADER, ShopApiControllerTests.TRACE_HEADER_VALUE)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, ShopApiControllerTests.TRACE_HEADER_VALUE)
				.runWithServer();
	}
	
	@Test
	public void testWithoutTraceIdSet() throws Exception {
		ServerCall sc = this.stub()
				.withGET("/trace-id-test-callback")
				.build();
		
		this.test()
				.withGET("/trace-id-test")
				.expectHeader(ShopApiConstants.TRACE_HEADER, this.traceId)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, this.traceId)
				.runWithServer();
	}
	
	@Test
	public void testTraceIdWithBadRequest() throws Exception {
		ServerCall sc = this.stub()
				.withGET("/trace-id-test-callback")
				.withStatus(400)
				.build();
		
		this.test()
				.withGET("/trace-id-test")
				.expectHeader(ShopApiConstants.TRACE_HEADER, this.traceId)
				.expectServerCall(sc)
				.expectServerHeader(ShopApiConstants.TRACE_HEADER, this.traceId)
				.runWithServer();
	}
	
	@Test
	public void testTraceIdWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/trace-id-error-test"))
				.andExpect(MockMvcResultMatchers.status().is(555))
				.andExpect(MockMvcResultMatchers.status().reason("FAILURE"))
				.andExpect(MockMvcResultMatchers.header().string(ShopApiConstants.TRACE_HEADER, this.traceId));
	}
}
