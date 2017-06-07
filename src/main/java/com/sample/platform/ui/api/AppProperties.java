package com.sample.platform.ui.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ext")
public class AppProperties {
	@Value("${ext.localization.url}")
	private String localizationUrl;
	
	@Value("${ext.localization.timeout}")
	private String localizationTimeout;
	
	@Value("${ext.order.url}")
	private String orderUrl;
	
	@Value("${ext.order.timeout}")
	private String orderTimeout;
	
	@Value("${ext.inventory.url}")
	private String inventoryUrl;
	
	@Value("${ext.inventory.timeout}")
	private String inventoryTimeout;
	
	@Value("${ext.content.url}")
	private String contentUrl;
	
	@Value("${ext.content.timeout}")
	private String contentTimeout;
	
	@Value("${ext.cart.url}")
	private String cartUrl;
	
	@Value("${ext.cart.timeout}")
	private String cartTimeout;

	@Value("${ext.user.url}")
	private String userUrl;
	
	@Value("${ext.user.timeout}")
	private String userTimeout;
	
	@Value("${ext.search.url}")
	private String searchUrl;
	
	@Value("${ext.search.timeout}")
	private String searchTimeout;
	
	@Value("${ext.checkout.url}")
	private String checkoutUrl;
	
	@Value("${ext.checkout.timeout}")
	private String checkoutTimeout;
	
	@Value("${ext.advertisement.url}")
	private String advertisementUrl;
	
	@Value("${ext.advertisement.timeout}")
	private String advertisementTimeout;
	
	@Value("${ext.review.url}")
	private String reviewUrl;
	
	@Value("${ext.review.timeout}")
	private String reviewTimeout;
	
	@Value("${ext.recommendation.url}")
	private String recommendationUrl;
	
	@Value("${ext.recommendation.timeout}")
	private String recommendationTimeout;
	
	@Value("${ext.messaging.url}")
	private String messagingUrl;
	
	@Value("${ext.messaging.timeout}")
	private String messagingTimeout;
	
	public String getLocalizationUrl() {
		return localizationUrl;
	} 
	
	public String getLocalizationTimeout() {
		return localizationTimeout;
	}
	
	public String getOrderUrl() {
		return orderUrl;
	} 
	
	public String getOrderTimeout() {
		return orderTimeout;
	}
	
	public String getInventoryUrl() {
		return inventoryUrl;
	}

	public String getInventoryTimeout() {
		return inventoryTimeout;
	}
	
	public String getContentUrl() {
		return contentUrl;
	}

	public String getContentTimeout() {
		return contentTimeout;
	}
	
	public String getCartUrl() {
		return cartUrl;
	} 
	
	public String getCartTimeout() {
		return cartTimeout;
	}
	
	public String getUserUrl() {
		return userUrl;
	}

	public String getUserTimeout() {
		return userTimeout;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public String getSearchTimeout() {
		return searchTimeout;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public String getCheckoutTimeout() {
		return checkoutTimeout;
	}
	
	public String getAdvertisementUrl() {
		return advertisementUrl;
	}
	
	public String getAdvertisementTimeout() {
		return advertisementTimeout;
	}
	
	public String getRecommendationUrl() {
		return recommendationUrl;
	}

	public String getRecommendationTimeout() {
		return recommendationTimeout;
	}
	
	public String getReviewUrl() {
		return reviewUrl;
	}

	public String getReviewTimeout() {
		return reviewTimeout;
	}
	
	public String getMessagingUrl() {
		return messagingUrl;
	}

	public String getMessagingTimeout() {
		return messagingTimeout;
	}
}