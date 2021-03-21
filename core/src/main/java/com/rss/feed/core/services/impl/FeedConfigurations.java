package com.rss.feed.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
	* OSGI configuration class for RSS Feed
	*/
@ObjectClassDefinition(name = "RSS Feed Configuration", description = "This configuration is for the RSS Feed configuration")
public @interface FeedConfigurations {
				
				@AttributeDefinition(name = "RSS Feed Endpoint", description = "RSS Feed endpoint url to fetch feed xml",
								type = AttributeType.STRING)
				String url();
}
