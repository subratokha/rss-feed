package com.rss.feed.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class FeedModel {
				
				@ValueMapValue
				@Default(values = "1")
				private String interval;
				
				public String getInterval() {
								return interval;
				}
}
