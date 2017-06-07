package com.sample.platform.ui.api.web.client;

import java.lang.reflect.Type;
import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.sample.platform.ui.api.service.HeaderBuilder;
import com.sample.platform.ui.api.web.client.ShopApiAsyncRestTemplate;

public class ShopApiAsyncRestTemplateTest {
	private ShopApiAsyncRestTemplate tpl;
	private HeaderBuilder hb;
	
	@Before
	public void setUp() {
		this.hb = Mockito.mock(HeaderBuilder.class);
		this.tpl = Mockito.spy(new ShopApiAsyncRestTemplate(hb, null));
	}
	
	@Test
	public void testHttpEntityCallbackMustCallBuildHeaders() {
		HttpEntity<?> in = Mockito.mock(HttpEntity.class);
		HttpEntity<?> out = Mockito.mock(HttpEntity.class);
		
		Mockito.doReturn(out).when(this.tpl).buildHeaders(in);
		
		Assert.assertNotNull(this.tpl.httpEntityCallback(in));
		
		Mockito.verify(this.tpl).buildHeaders(in);
	}
	
	@Test
	public void testHttpEntityCallbackWithTypeMustCallBuildHeaders() {
		HttpEntity<?> in = Mockito.mock(HttpEntity.class);
		HttpEntity<?> out = Mockito.mock(HttpEntity.class);
		Type rt = Mockito.mock(Type.class);
		
		Mockito.doReturn(out).when(this.tpl).buildHeaders(in);
		
		Assert.assertNotNull(this.tpl.httpEntityCallback(in, rt));
		
		Mockito.verify(this.tpl).buildHeaders(in);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testBuildHeadersMustNotAcceptResponseEntity() {
		this.tpl.buildHeaders(new ResponseEntity<String>(HttpStatus.ACCEPTED));
	}
	
	@Test
	public void testBuildHeadersMustAcceptNulls() {
		Assert.assertNull(this.tpl.buildHeaders(null));
	}
	
	@Test
	public void testBuildHeadersMustAcceptHttpEntity() throws Exception {
		HttpHeaders in = new HttpHeaders();
		HttpHeaders out = new HttpHeaders();
		
		HttpEntity<String> e = new HttpEntity<>("data", in);
		
		Mockito.doReturn(out).when(this.hb).build(in, null);
		
		Assert.assertEquals(new HttpEntity<String>("data", out), this.tpl.buildHeaders(e));
	}
	
	@Test
	public void testBuildHeadersMustAcceptRequestEntity() throws Exception {
		HttpHeaders in = new HttpHeaders();
		HttpHeaders out = new HttpHeaders();
		
		RequestEntity<String> e = new RequestEntity<>(in, HttpMethod.POST, new URI("http://example.com"));
		
		Mockito.doReturn(out).when(this.hb).build(in, null);
		
		Assert.assertEquals(new RequestEntity<String>(out, HttpMethod.POST, new URI("http://example.com")),
				this.tpl.buildHeaders(e));
	}
}
