package com.rss.feed.core.services;

import com.rss.feed.core.dtos.Rss;

import java.io.IOException;

/**
	* FeedService Interface
	*/
public interface FeedService {
				
				/**
					* Get RSS Feed
					*
					* @return Rss Object
					*
					* @throws IOException
					*/
				Rss getFeed() throws IOException;
				
}
