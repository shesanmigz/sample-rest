package com.sample.platform.ui.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.ShopApiConstants;
import com.sample.platform.ui.api.service.impl.HeaderBuilderImpl.AllowedHeaderDefinition;
import com.sample.platform.ui.api.tokens.Token;
import com.sample.platform.ui.api.web.client.exceptions.BadRequestException;

public class HeaderBuilderImplTest {
	private HeaderBuilderImpl hb;
	
	private final List<AllowedHeaderDefinition> simple = Lists.newArrayList(
			new AllowedHeaderDefinition("Hd1", ImmutableList.of("^[0-9]+.+")),
			new AllowedHeaderDefinition("hD2", ImmutableList.of(".+[0-9]+$")),
			new AllowedHeaderDefinition("HD3", ImmutableList.of(".*a.*", "^b.*")));
	
	private final List<AllowedHeaderDefinition> complex = Lists.newArrayList(
			new AllowedHeaderDefinition("X-Sample-Session",
					ImmutableList.of("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")),
			new AllowedHeaderDefinition("X-Sample-Domain",
					ImmutableList.of("(?=^.{1,253}$)(^(((?!-)[a-zA-Z0-9-]{1,63}(?<!-))|((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,63})$)")),
			new AllowedHeaderDefinition("X-Sample-Language", ImmutableList.of("[a-z][a-z]")),
			new AllowedHeaderDefinition("X-Sample-Location",
					ImmutableList.of("[+-]?[0-9]+(\\.[0-9]+)?\\s+[+-]?[0-9]+(\\.[0-9]+)?")),
			new AllowedHeaderDefinition("X-Sample-RemoteIp", ImmutableList.of(
					"^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
					"(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))")));
	
	@Before
	public void setUp() {
		this.hb = Mockito.spy(new HeaderBuilderImpl(100, 100, 1000, false, null, null));
	}
	
	@Test
	public void testBuildMustExcludedUnwantedHeaders() {
		this.hb.getHeaders().addAll(this.simple);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("a", "a");
		hs.add("b", "b");
		hs.add("hd1", "1a");
		hs.add("hd2", "a1");
		hs.add("Hd3", "b");
		
		Map<String, String> exp = ImmutableMap.<String, String> builder().put("Hd1", "1a").put("hD2", "a1").put("HD3", "b")
				.build();
		
		Assert.assertEquals(exp, this.hb.build(hs, null).toSingleValueMap());
	}
	
	@Test
	public void testBuildMustExcludedUnwantedHeaders2() {
		this.hb.getHeaders().addAll(this.simple);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("a", "a");
		hs.add("b", "b");
		hs.add("hd1", "1a");
		hs.add("hD2", "a1");
		hs.add("hd3", "b");
		
		Map<String, String> exp = ImmutableMap.<String, String> builder().put("Hd1", "1a").put("hD2", "a1").put("HD3", "b")
				.build();
		
		Assert.assertEquals(exp, this.hb.build(hs, null).toSingleValueMap());
	}
	
	@Test
	public void testBuildMustFailOnInvalidHeaderValue() {
		this.hb.getHeaders().addAll(this.simple);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("a", "a");
		hs.add("b", "b");
		hs.add("hd1", "1a");
		hs.add("hd2", "a");
		hs.add("hd3", "b");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals("a is not a valid value for hD2; patterns used: .+[0-9]+$", ex.getMessage());
		}
	}
	
	@Test
	public void testBuildHeadersMustFailOnHeaderMultiValue() {
		this.hb.getHeaders().addAll(this.simple);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("a", "a");
		hs.add("b", "b");
		hs.add("hd1", "1a");
		hs.add("hd2", "a1");
		hs.add("hd3", "b");
		hs.add("hd3", "a");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals("HD3 may have only one value; multi-value headers are not supported.", ex.getMessage());
		}
	}
	
	@Test
	public void testRealPatterns() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("a", "a");
		hs.add("b", "b");
		hs.add("x-Sample-Session", "7fecf3a9-5c89-43b9-a1eb-2d2c3c9d5233");
		hs.add("X-Sample-Domain", "xn--nnx388a.com");
		hs.add("x-Sample-remoteip", "2001:0db8:85a3::8a2e:0370:7334");
		hs.add("x-Sample-language", "de");
		hs.add("X-Sample-location", "-37.11   11.78");
		
		Map<String, String> exp = ImmutableMap.<String, String> builder()
				.put("X-Sample-Session", "7fecf3a9-5c89-43b9-a1eb-2d2c3c9d5233").put("X-Sample-Domain", "xn--nnx388a.com")
				.put("X-Sample-RemoteIp", "2001:0db8:85a3::8a2e:0370:7334").put("X-Sample-Language", "de")
				.put("X-Sample-Location", "-37.11   11.78").build();
		
		Assert.assertEquals(exp, this.hb.build(hs, null).toSingleValueMap());
	}
	
	@Test
	public void testRealPatterns2() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("a", "a");
		hs.add("b", "b");
		hs.add("X-Sample-Session", "7390128a-634f-42f0-a7e0-8baf94ee4dc7");
		hs.add("X-Sample-domain", "xn--j1ail.info");
		hs.add("x-Sample-RemoteIp", "167.81.188.1");
		hs.add("X-Sample-language", "gb");
		hs.add("x-Sample-location", "37.11 +11.78");
		
		Map<String, String> exp = ImmutableMap.<String, String> builder()
				.put("X-Sample-Session", "7390128a-634f-42f0-a7e0-8baf94ee4dc7").put("X-Sample-Domain", "xn--j1ail.info")
				.put("X-Sample-RemoteIp", "167.81.188.1").put("X-Sample-Language", "gb").put("X-Sample-Location", "37.11 +11.78")
				.build();
		
		Assert.assertEquals(exp, this.hb.build(hs, null).toSingleValueMap());
	}
	
	@Test
	public void testLocalhostDomain() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-domain", "localhost");
		
		Assert.assertEquals(ImmutableMap.<String, String> builder().put("X-Sample-Domain", "localhost").build(),
				this.hb.build(hs, null).toSingleValueMap());
	}

	@Test
	public void testDefaultDomain() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-domain", "www.xsampledomain-header.com");
		
		Assert.assertEquals(ImmutableMap.<String, String> builder().put("X-Sample-Domain", "www.xsampledomain-header.com").build(),
				this.hb.build(hs, null).toSingleValueMap());
	}
	
	@Test
	public void testBadUUID() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-Session", "7390128a-634f842f0-a7e0-8baf94ee4dc7");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals(
					"7390128a-634f842f0-a7e0-8baf94ee4dc7 is not a valid value for X-Sample-Session; patterns used: ^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",
					ex.getMessage());
		}
	}
	
	@Test
	public void testBadDomain() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-Domain", "-who.com");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals(
					"-who.com is not a valid value for X-Sample-Domain; patterns used: (?=^.{1,253}$)(^(((?!-)[a-zA-Z0-9-]{1,63}(?<!-))|((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,63})$)",
					ex.getMessage());
		}
	}
	
	@Test
	public void testBadIPV4() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-RemoteIp", "127.0.0.256");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals(
					"127.0.0.256 is not a valid value for X-Sample-RemoteIp; patterns used: ^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$;(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))",
					ex.getMessage());
		}
	}
	
	@Test
	public void testBadIPV6() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-RemoteIp", "2001:0db8:85a3:::8a2e:0370:7334");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals(
					"2001:0db8:85a3:::8a2e:0370:7334 is not a valid value for X-Sample-RemoteIp; patterns used: ^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$;(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))",
					ex.getMessage());
		}
	}
	
	@Test
	public void testBadLanguage() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-Language", "usa");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals("usa is not a valid value for X-Sample-Language; patterns used: [a-z][a-z]", ex.getMessage());
		}
	}
	
	@Test
	public void testBadLocation() {
		this.hb.getHeaders().addAll(this.complex);
		
		HttpHeaders hs = new HttpHeaders();
		hs.add("X-Sample-Location", "12.a -89.8");
		
		try {
			this.hb.build(hs, null);
			Assert.fail();
		} catch (final BadRequestException ex) {
			Assert.assertEquals(
					"12.a -89.8 is not a valid value for X-Sample-Location; patterns used: [+-]?[0-9]+(\\.[0-9]+)?\\s+[+-]?[0-9]+(\\.[0-9]+)?",
					ex.getMessage());
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void testBuildShouldThrowIfTokenIsMissing() throws Exception {
		HeaderBuilderImpl hb = new HeaderBuilderImpl(1000 * 60 * 60 * 24, 1000 * 60 * 60 * 24, 1000, true, null, null);
		
		try {
			hb.build(new HttpHeaders(), null);
		} finally {
			hb.destroy();
		}
	}
	
	@Test
	public void testBuildShouldAddAuthHeader() throws Exception {
		HeaderBuilderImpl hb = new HeaderBuilderImpl(1000 * 60 * 60 * 24, 1000 * 60 * 60 * 24, 1000, true, null, null);
		
		try {
			Token token = new Token("tokenA",
					ImmutableMap.of("exp", (long) Math.floor(new Date().getTime() / 1000.0) + 60 * 60 * 24));
			hb.getCache().put("tokenA", token);
			
			Assert.assertEquals(ImmutableMap.of("Authorization", "Bearer tokenA"),
					hb.build(new HttpHeaders(), null).toSingleValueMap());
		} finally {
			hb.destroy();
		}
	}
	
	@Test
	public void testBuildShouldAddTraceHeader() throws Exception {
		Assert.assertEquals(ImmutableMap.of(), hb.build(new HttpHeaders(), null).toSingleValueMap());
		
		Assert.assertEquals(ImmutableMap.of(ShopApiConstants.TRACE_HEADER, "a"),
				hb.build(new HttpHeaders(), "a").toSingleValueMap());
	}
}
