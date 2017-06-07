package com.sample.platform.ui.api.tokens;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sample.platform.ui.api.tokens.PublicKeyUpdatingCacheManager;
import com.sample.platform.ui.api.tokens.Token;
import com.sample.platform.ui.api.tokens.TokenUtils;

public class TokenUtilsTest {
	private final TokenUtils tokenUtils = new TokenUtils();
	
	@Test
	public void testSafeToken() {
		Assert.assertNull(this.tokenUtils.safeToken(null));
		Assert.assertEquals("", this.tokenUtils.safeToken(""));
		Assert.assertEquals("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9",
				this.tokenUtils.safeToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9"));
		Assert.assertEquals("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9",
				this.tokenUtils.safeToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9."));
		Assert.assertEquals(
				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibG9jYWxpemF0aW9uIiwidXNlci10cmFja2luZyIsInJlY29tbWVuZGF0aW9uIiwiaW52ZW50b3J5IiwiY2FydCIsInNlYXJjaCIsImNvbnRlbnQtYXBpLXVpIiwicmV2aWV3IiwiYWR2ZXJ0aXNlbWVudCIsInBheW1lbnQiLCJ1c2VyIiwiY2hlY2tvdXQiLCJvcmRlciJdLCJzY29wZSI6WyJzZWFyY2hfY2xpZW50IiwibG9jYWxpemF0aW9uX2NsaWVudCIsImNhcnRfY2xpZW50IiwicmVjb21tZW5kYXRpb25fY2xpZW50Iiwib3JkZXJfY2xpZW50IiwidXNlci10cmFja2luZ19jbGllbnQiLCJpbnZlbnRvcnlfY2xpZW50IiwidXNlcl9jbGllbnQiLCJwYXltZW50X2NsaWVudCIsInJldmlld19jbGllbnQiLCJjb250ZW50LWFwaS11aV9jbGllbnQiLCJjaGVja291dF9jbGllbnQiLCJhZHZlcnRpc2VtZW50X2NsaWVudCJdLCJleHAiOjE0OTQ4NTM4NDYsImp0aSI6ImM5Y2VlMGEzLWI1NTktNGUzNy1hMzk4LTg4ZTA5YjEzYWMxMiIsImNsaWVudF9pZCI6IkREWDdZSDZLOTA0RkJGMU85SVlBIn0",
				this.tokenUtils.safeToken(
						"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibG9jYWxpemF0aW9uIiwidXNlci10cmFja2luZyIsInJlY29tbWVuZGF0aW9uIiwiaW52ZW50b3J5IiwiY2FydCIsInNlYXJjaCIsImNvbnRlbnQtYXBpLXVpIiwicmV2aWV3IiwiYWR2ZXJ0aXNlbWVudCIsInBheW1lbnQiLCJ1c2VyIiwiY2hlY2tvdXQiLCJvcmRlciJdLCJzY29wZSI6WyJzZWFyY2hfY2xpZW50IiwibG9jYWxpemF0aW9uX2NsaWVudCIsImNhcnRfY2xpZW50IiwicmVjb21tZW5kYXRpb25fY2xpZW50Iiwib3JkZXJfY2xpZW50IiwidXNlci10cmFja2luZ19jbGllbnQiLCJpbnZlbnRvcnlfY2xpZW50IiwidXNlcl9jbGllbnQiLCJwYXltZW50X2NsaWVudCIsInJldmlld19jbGllbnQiLCJjb250ZW50LWFwaS11aV9jbGllbnQiLCJjaGVja291dF9jbGllbnQiLCJhZHZlcnRpc2VtZW50X2NsaWVudCJdLCJleHAiOjE0OTQ4NTM4NDYsImp0aSI6ImM5Y2VlMGEzLWI1NTktNGUzNy1hMzk4LTg4ZTA5YjEzYWMxMiIsImNsaWVudF9pZCI6IkREWDdZSDZLOTA0RkJGMU85SVlBIn0"));
		Assert.assertEquals(
				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibG9jYWxpemF0aW9uIiwidXNlci10cmFja2luZyIsInJlY29tbWVuZGF0aW9uIiwiaW52ZW50b3J5IiwiY2FydCIsInNlYXJjaCIsImNvbnRlbnQtYXBpLXVpIiwicmV2aWV3IiwiYWR2ZXJ0aXNlbWVudCIsInBheW1lbnQiLCJ1c2VyIiwiY2hlY2tvdXQiLCJvcmRlciJdLCJzY29wZSI6WyJzZWFyY2hfY2xpZW50IiwibG9jYWxpemF0aW9uX2NsaWVudCIsImNhcnRfY2xpZW50IiwicmVjb21tZW5kYXRpb25fY2xpZW50Iiwib3JkZXJfY2xpZW50IiwidXNlci10cmFja2luZ19jbGllbnQiLCJpbnZlbnRvcnlfY2xpZW50IiwidXNlcl9jbGllbnQiLCJwYXltZW50X2NsaWVudCIsInJldmlld19jbGllbnQiLCJjb250ZW50LWFwaS11aV9jbGllbnQiLCJjaGVja291dF9jbGllbnQiLCJhZHZlcnRpc2VtZW50X2NsaWVudCJdLCJleHAiOjE0OTQ4NTM4NDYsImp0aSI6ImM5Y2VlMGEzLWI1NTktNGUzNy1hMzk4LTg4ZTA5YjEzYWMxMiIsImNsaWVudF9pZCI6IkREWDdZSDZLOTA0RkJGMU85SVlBIn0",
				this.tokenUtils.safeToken(
						"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibG9jYWxpemF0aW9uIiwidXNlci10cmFja2luZyIsInJlY29tbWVuZGF0aW9uIiwiaW52ZW50b3J5IiwiY2FydCIsInNlYXJjaCIsImNvbnRlbnQtYXBpLXVpIiwicmV2aWV3IiwiYWR2ZXJ0aXNlbWVudCIsInBheW1lbnQiLCJ1c2VyIiwiY2hlY2tvdXQiLCJvcmRlciJdLCJzY29wZSI6WyJzZWFyY2hfY2xpZW50IiwibG9jYWxpemF0aW9uX2NsaWVudCIsImNhcnRfY2xpZW50IiwicmVjb21tZW5kYXRpb25fY2xpZW50Iiwib3JkZXJfY2xpZW50IiwidXNlci10cmFja2luZ19jbGllbnQiLCJpbnZlbnRvcnlfY2xpZW50IiwidXNlcl9jbGllbnQiLCJwYXltZW50X2NsaWVudCIsInJldmlld19jbGllbnQiLCJjb250ZW50LWFwaS11aV9jbGllbnQiLCJjaGVja291dF9jbGllbnQiLCJhZHZlcnRpc2VtZW50X2NsaWVudCJdLCJleHAiOjE0OTQ4NTM4NDYsImp0aSI6ImM5Y2VlMGEzLWI1NTktNGUzNy1hMzk4LTg4ZTA5YjEzYWMxMiIsImNsaWVudF9pZCI6IkREWDdZSDZLOTA0RkJGMU85SVlBIn0."));
		Assert.assertEquals(
				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibG9jYWxpemF0aW9uIiwidXNlci10cmFja2luZyIsInJlY29tbWVuZGF0aW9uIiwiaW52ZW50b3J5IiwiY2FydCIsInNlYXJjaCIsImNvbnRlbnQtYXBpLXVpIiwicmV2aWV3IiwiYWR2ZXJ0aXNlbWVudCIsInBheW1lbnQiLCJ1c2VyIiwiY2hlY2tvdXQiLCJvcmRlciJdLCJzY29wZSI6WyJzZWFyY2hfY2xpZW50IiwibG9jYWxpemF0aW9uX2NsaWVudCIsImNhcnRfY2xpZW50IiwicmVjb21tZW5kYXRpb25fY2xpZW50Iiwib3JkZXJfY2xpZW50IiwidXNlci10cmFja2luZ19jbGllbnQiLCJpbnZlbnRvcnlfY2xpZW50IiwidXNlcl9jbGllbnQiLCJwYXltZW50X2NsaWVudCIsInJldmlld19jbGllbnQiLCJjb250ZW50LWFwaS11aV9jbGllbnQiLCJjaGVja291dF9jbGllbnQiLCJhZHZlcnRpc2VtZW50X2NsaWVudCJdLCJleHAiOjE0OTQ4NTM4NDYsImp0aSI6ImM5Y2VlMGEzLWI1NTktNGUzNy1hMzk4LTg4ZTA5YjEzYWMxMiIsImNsaWVudF9pZCI6IkREWDdZSDZLOTA0RkJGMU85SVlBIn0",
				this.tokenUtils.safeToken(
						"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibG9jYWxpemF0aW9uIiwidXNlci10cmFja2luZyIsInJlY29tbWVuZGF0aW9uIiwiaW52ZW50b3J5IiwiY2FydCIsInNlYXJjaCIsImNvbnRlbnQtYXBpLXVpIiwicmV2aWV3IiwiYWR2ZXJ0aXNlbWVudCIsInBheW1lbnQiLCJ1c2VyIiwiY2hlY2tvdXQiLCJvcmRlciJdLCJzY29wZSI6WyJzZWFyY2hfY2xpZW50IiwibG9jYWxpemF0aW9uX2NsaWVudCIsImNhcnRfY2xpZW50IiwicmVjb21tZW5kYXRpb25fY2xpZW50Iiwib3JkZXJfY2xpZW50IiwidXNlci10cmFja2luZ19jbGllbnQiLCJpbnZlbnRvcnlfY2xpZW50IiwidXNlcl9jbGllbnQiLCJwYXltZW50X2NsaWVudCIsInJldmlld19jbGllbnQiLCJjb250ZW50LWFwaS11aV9jbGllbnQiLCJjaGVja291dF9jbGllbnQiLCJhZHZlcnRpc2VtZW50X2NsaWVudCJdLCJleHAiOjE0OTQ4NTM4NDYsImp0aSI6ImM5Y2VlMGEzLWI1NTktNGUzNy1hMzk4LTg4ZTA5YjEzYWMxMiIsImNsaWVudF9pZCI6IkREWDdZSDZLOTA0RkJGMU85SVlBIn0.YYiZBnDuuJ0Musl51Cu-qtwA1HWxHS8DC8CMahoDgwuT-NFrc3xGvwFJtD-7HOuHhQzM8_63vEigLW1V4Tzacm2aPwHq-qwNOY7rHMFrTWJfJ8LiUkO9oBLOEH3t_ypPxaLE4QWfgIglbqPGVAYSQ4MskflsYX5f2uyu5h2Uf1HQfG3j69-N4PB3pqDKJ1kWp1Vi4-we2XdvwPQgonDLFIyJT4X3NF763MMTz85yBZZ4U34H3CqsfR2QpJKWkWh0Z18o_X2Fi2K5xDKvNi2TzpgtkZXR8sJ7KKdjXl2DX43DSK-d92ppnwOALOJWtUtp3dMIyfYI_zQZ27CigmAAig"));
	}
	
	@Test
	public void testFindToken() {
		long t = (long) Math.floor(new Date().getTime() / 1000.0);
		
		Token tk1 = Mockito.mock(Token.class);
		Mockito.doReturn(t + 1).when(tk1).getExpiresAt();
		
		Token tk2 = Mockito.mock(Token.class);
		Mockito.doReturn(t + 2).when(tk2).getExpiresAt();
		
		Token tk3 = Mockito.mock(Token.class);
		Mockito.doReturn(t + 60 * 60 * 24).when(tk3).getExpiresAt();
		Mockito.doReturn(t + 60 * 60 * 24 - 1).when(tk3).getNotBefore();
		
		Token tk4 = Mockito.mock(Token.class);
		Mockito.doReturn(t - 1).when(tk4).getExpiresAt();
		
		Assert.assertEquals(tk2, this.tokenUtils.findToken(ImmutableList.of(tk1, tk2, tk3, tk4)));
	}
	
	@Test
	public void testVerifyDates() {
		Token token = Mockito.mock(Token.class);
		
		Mockito.doReturn((long) Math.floor(new Date().getTime() / 1000.0) + 60 * 60 * 24).when(token).getExpiresAt();
		Mockito.doReturn(null).when(token).getIssuedAt();
		Mockito.doReturn(null).when(token).getNotBefore();
		
		Assert.assertTrue(this.tokenUtils.verifyDates(token));
		
		Mockito.doReturn((long) Math.floor(new Date().getTime() / 1000.0) - 1).when(token).getExpiresAt();
		
		Assert.assertFalse(this.tokenUtils.verifyDates(token));
	}
	
	@Test
	public void testUseBestKey() {
		PublicKeyUpdatingCacheManager cm = Mockito.mock(PublicKeyUpdatingCacheManager.class);
		RsaVerifier v1 = Mockito.mock(RsaVerifier.class);
		RsaVerifier v2 = Mockito.mock(RsaVerifier.class);
		
		Mockito.doReturn(new ConcurrentHashMap<>(ImmutableMap.of("v1", v1, "v2", v2))).when(cm).getCache();
		
		Function<RsaVerifier, String> f = Mockito.spy(new Function<RsaVerifier, String>() {
			@Override
			public String apply(final RsaVerifier v) {
				if (v == v1) {
					throw new RuntimeException();
				}
				return "O";
			}
		});
		
		Assert.assertEquals("O", this.tokenUtils.useBestKey("tk", cm, f));
		
		Mockito.verify(f, Mockito.times(2)).apply(Mockito.any());
		Mockito.verify(f).apply(v1);
		Mockito.verify(f).apply(v2);
	}
}
