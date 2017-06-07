package com.sample.platform.ui.api.security;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class SecurityTestUtils {
	public static final String KEY_RES_PATH = "api-authorization-test";
	public static final String PUB_KEY_RES_PATH = "api-authorization-test.crt";
	
	public String genToken(final long timeOffset, final String... audience) throws Exception {
		return this.genToken(timeOffset, Lists.newArrayList(audience), ImmutableList.of("write"));
	}
	
	public String genToken(final long timeOffset, final List<String> audience, final List<String> scopes)
			throws Exception {
		String token = null;
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(KEY_RES_PATH)) {
			byte[] bytes = Base64.decodeBase64(IOUtils.toByteArray(is));
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAKey privKey = (RSAKey) keyFactory.generatePrivate(keySpec);
			
			token = JWT.create().withAudience(audience.toArray(new String[scopes.size()]))
					.withArrayClaim("scope", scopes.toArray(new String[scopes.size()]))
					.withExpiresAt(new Date(new Date().getTime() + timeOffset)).sign(Algorithm.RSA256(privKey));
		}
		
		return token;
	}
}
