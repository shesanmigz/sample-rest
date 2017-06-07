package com.sample.platform.ui.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.base.Strings;

/**
 * Base class for reading data from an AWS bucket.
 */
public abstract class AWSReader<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWSReader.class);
	
	protected abstract String getBucketName();
	
	protected abstract String getPrefix();
	
	protected abstract String getRegionName();
	
	protected abstract String getAccessKey();
	
	protected abstract String getSecretKey();
	
	/**
	 * Sub classes should populate destination with the data obtained from the S3 object.
	 */
	protected abstract void readItem(T destination, S3Object object) throws Exception;
	
	/**
	 * Reads data from an AWS bucket into the provided destination.
	 */
	protected void read(final T destination) throws Exception {
		AWSReader.LOGGER.debug("Reading from bucket {}", this.getBucketName());
		
		AmazonS3 s3Client = this.buildClient();
		
		ObjectListing listing = s3Client.listObjects(this.getBucketName(), this.getPrefix());
		do {
			this.readItems(destination, s3Client, listing.getObjectSummaries());
			
			if (!listing.isTruncated()) {
				break;
			}
			
			listing = s3Client.listNextBatchOfObjects(listing);
		} while (true);
	}
	
	private void readItems(final T destination, final AmazonS3 s3Client, final List<S3ObjectSummary> items)
			throws Exception {
		for (final S3ObjectSummary item : items) {
			S3Object object = s3Client.getObject(new GetObjectRequest(this.getBucketName(), item.getKey()));
			if (!object.getObjectMetadata().getContentType().equals("application/x-directory")) {
				AWSReader.LOGGER.trace("Reading from file {}", item.getKey());
				
				this.readItem(destination, object);
			}
		}
	}
	
	AmazonS3 buildClient() {
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard().withCredentials(
				new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.getAccessKey(), this.getSecretKey())));
		
		if (!Strings.nullToEmpty(this.getRegionName()).trim().isEmpty()) {
			builder.withRegion(this.getRegionName());
		}
		
		return builder.build();
	}
}
