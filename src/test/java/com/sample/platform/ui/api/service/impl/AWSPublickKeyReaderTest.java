package com.sample.platform.ui.api.service.impl;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.collect.ImmutableList;
import com.sample.platform.ui.api.service.impl.AWSPublicKeyReader;

public class AWSPublickKeyReaderTest {
	private AWSPublicKeyReader r;
	
	@Before
	public void setUp() {
		this.r = Mockito.spy(new AWSPublicKeyReader());
		Mockito.doReturn("bn").when(this.r).getBucketName();
		Mockito.doReturn("pre").when(this.r).getPrefix();
	}
	
	@Test
	public void testGetKeys() throws Exception {
		AmazonS3 s3 = Mockito.mock(AmazonS3.class);
		ObjectListing ol0 = Mockito.mock(ObjectListing.class);
		ObjectListing ol1 = Mockito.mock(ObjectListing.class);
		S3ObjectSummary os1 = Mockito.mock(S3ObjectSummary.class);
		S3ObjectSummary os2 = Mockito.mock(S3ObjectSummary.class);
		S3ObjectSummary osZ = Mockito.mock(S3ObjectSummary.class);
		S3Object o1 = Mockito.mock(S3Object.class);
		S3Object o2 = Mockito.mock(S3Object.class);
		S3Object oZ = Mockito.mock(S3Object.class);
		InputStream is1 = Mockito.mock(S3ObjectInputStream.class);
		InputStream is2 = Mockito.mock(S3ObjectInputStream.class);
		InputStream isZ = Mockito.mock(S3ObjectInputStream.class);
		ObjectMetadata om1 = Mockito.mock(ObjectMetadata.class);
		ObjectMetadata om2 = Mockito.mock(ObjectMetadata.class);
		ObjectMetadata omz = Mockito.mock(ObjectMetadata.class);
		
		Mockito.doAnswer(new TestAnswer("key1")).when(is1).read(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
		Mockito.doAnswer(new TestAnswer("key2\n\n")).when(is2).read(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
		Mockito.doAnswer(new TestAnswer("keyB\n\n\nkeyA\n")).when(isZ).read(Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt());
		
		Mockito.doReturn(is1).when(o1).getObjectContent();
		Mockito.doReturn(om1).when(o1).getObjectMetadata();
		Mockito.doReturn("text/plain").when(om1).getContentType();
		Mockito.doReturn(is2).when(o2).getObjectContent();
		Mockito.doReturn(om2).when(o2).getObjectMetadata();
		Mockito.doReturn("text/plain").when(om2).getContentType();
		Mockito.doReturn(isZ).when(oZ).getObjectContent();
		Mockito.doReturn(omz).when(oZ).getObjectMetadata();
		Mockito.doReturn("text/plain").when(omz).getContentType();
		Mockito.doReturn("k1").when(o1).getKey();
		Mockito.doReturn("k2").when(o2).getKey();
		Mockito.doReturn("kZ").when(oZ).getKey();
		Mockito.doReturn("k1").when(os1).getKey();
		Mockito.doReturn("k2").when(os2).getKey();
		Mockito.doReturn("kZ").when(osZ).getKey();
		Mockito.doReturn(ol0).when(s3).listObjects("bn", "pre");
		Mockito.doReturn(s3).when(this.r).buildClient();
		Mockito.doReturn(ImmutableList.of(os1, os2)).when(ol0).getObjectSummaries();
		Mockito.doReturn(ImmutableList.of(osZ)).when(ol1).getObjectSummaries();
		Mockito.doReturn(true).when(ol0).isTruncated();
		Mockito.doReturn(ol1).when(s3).listNextBatchOfObjects(ol0);
		Mockito.doReturn(false).when(ol1).isTruncated();
		Mockito.doAnswer(ctx -> {
			Assert.assertEquals(1, ctx.getArguments().length);
			GetObjectRequest or = ctx.getArgumentAt(0, GetObjectRequest.class);
			Assert.assertEquals("bn", or.getBucketName());
			S3Object o = null;
			if (or.getKey().equals("k1")) {
				o = o1;
			} else if (or.getKey().equals("k2")) {
				o = o2;
			} else if (or.getKey().equals("kZ")) {
				o = oZ;
			} else {
				Assert.fail();
			}
			return o;
		}).when(s3).getObject(Mockito.any(GetObjectRequest.class));
		
		Assert.assertEquals(ImmutableList.of("key1", "key2\n\n", "keyB\n\n\nkeyA\n"), this.r.readPrivateKeys());
	}
	
	@Test
	public void testGetPartialKeys() throws Exception {
		RuntimeException e = new RuntimeException("Don't panic, the exception is expected by the test");
		
		AmazonS3 s3 = Mockito.mock(AmazonS3.class);
		ObjectListing ol0 = Mockito.mock(ObjectListing.class);
		ObjectListing ol1 = Mockito.mock(ObjectListing.class);
		S3ObjectSummary os1 = Mockito.mock(S3ObjectSummary.class);
		S3ObjectSummary os2 = Mockito.mock(S3ObjectSummary.class);
		S3Object o1 = Mockito.mock(S3Object.class);
		S3Object o2 = Mockito.mock(S3Object.class);
		ObjectMetadata om1 = Mockito.mock(ObjectMetadata.class);
		ObjectMetadata om2 = Mockito.mock(ObjectMetadata.class);
		InputStream is1 = Mockito.mock(S3ObjectInputStream.class);
		InputStream is2 = Mockito.mock(S3ObjectInputStream.class);
		
		Mockito.doAnswer(new TestAnswer("key1")).when(is1).read(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
		Mockito.doAnswer(new TestAnswer("key2\n\n")).when(is2).read(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
		
		Mockito.doReturn(is1).when(o1).getObjectContent();
		Mockito.doReturn("text/plain").when(om1).getContentType();
		Mockito.doReturn(om1).when(o1).getObjectMetadata();
		Mockito.doReturn(is2).when(o2).getObjectContent();
		Mockito.doReturn("text/plain").when(om2).getContentType();
		Mockito.doReturn(om2).when(o2).getObjectMetadata();
		Mockito.doReturn("k1").when(o1).getKey();
		Mockito.doReturn("k2").when(o2).getKey();
		Mockito.doReturn("k1").when(os1).getKey();
		Mockito.doReturn("k2").when(os2).getKey();
		Mockito.doReturn(ol0).when(s3).listObjects("bn", "pre");
		Mockito.doReturn(s3).when(this.r).buildClient();
		Mockito.doReturn(ImmutableList.of(os1, os2)).when(ol0).getObjectSummaries();
		Mockito.doThrow(e).when(ol1).getObjectSummaries();
		Mockito.doReturn(true).when(ol0).isTruncated();
		Mockito.doReturn(ol1).when(s3).listNextBatchOfObjects(ol0);
		Mockito.doReturn(false).when(ol1).isTruncated();
		Mockito.doAnswer(ctx -> {
			Assert.assertEquals(1, ctx.getArguments().length);
			GetObjectRequest or = ctx.getArgumentAt(0, GetObjectRequest.class);
			Assert.assertEquals("bn", or.getBucketName());
			S3Object o = null;
			if (or.getKey().equals("k1")) {
				o = o1;
			} else if (or.getKey().equals("k2")) {
				o = o2;
			} else {
				Assert.fail();
			}
			return o;
		}).when(s3).getObject(Mockito.any(GetObjectRequest.class));
		
		Assert.assertEquals(ImmutableList.of("key1", "key2\n\n"), this.r.readPrivateKeys());
		
		Mockito.verify(ol1).getObjectSummaries();
	}
	
	@Test
	public void testGetEmptyKeys() throws Exception {
		RuntimeException e = new RuntimeException("The exception is expected");
		AmazonS3 s3 = Mockito.mock(AmazonS3.class);
		Mockito.doThrow(e).when(s3).listObjects("bn", "pre");
		Mockito.doReturn(s3).when(this.r).buildClient();
		
		Assert.assertEquals(ImmutableList.of(), this.r.readPrivateKeys());
	}
}
